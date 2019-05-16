package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.ActiviteDTO;
import com.unamur.portaildesartistes.DTO.FormulaireDTO;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.skife.jdbi.v2.unstable.BindIn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

@Repository
public class DonneeFormulaireImpl extends Donnee<FormulaireDTO>{
    private static final Logger logger = LoggerFactory.getLogger(DonneeFormulaireImpl.class);

    public List<FormulaireDTO> getByCitoyenId(UUID p_id){ return super.Exec(FormulaireSQLs.class).getByCitoyenId( p_id ); }

    @Override
    public List<FormulaireDTO> list(){
        return super.Exec(FormulaireSQLs.class).list();
        /*try{
            List<FormulaireDTO> l = super.Exec(FormulaireSQLs.class).list();
            logger.error("list donnee OK"+l.size());
            return l;
        }catch(Exception e){
            logger.error(e.getMessage());
            for( StackTraceElement el : e.getStackTrace() )
                logger.error( el.getLineNumber() +":"+el.toString() );
            return new ArrayList<>();
        }
        */
    }
    @Override
    public FormulaireDTO getById(UUID p_id){ return super.Exec(FormulaireSQLs.class).getById( p_id ); }
    @Override
    public UUID insert(FormulaireDTO item){
        item.setId( UUID.fromString(super.Exec(FormulaireSQLs.class).insert(item)) );
        updateFormDetails(item);
        return item.getId();
    }
    @Override
    public void update(FormulaireDTO item){
        super.Exec(FormulaireActiviteSQLs.class).deleteByForm( item.getId() );
        super.Exec(FormulaireSQLs.class).update(item);
        updateFormDetails(item);
    }
    private void updateFormDetails(FormulaireDTO item){
        for( UUID actId : item.getActivitesId() )
            super.Exec(FormulaireActiviteSQLs.class).insert(actId,item.getId());
        super.Exec(FormulaireSQLs.class).updateCursusAc  (item.getId(),item.getCursusAc()  .toArray(new String[item.getCursusAc()  .size()]) );
        super.Exec(FormulaireSQLs.class).updateExpPro    (item.getId(),item.getExpPro()    .toArray(new String[item.getExpPro()    .size()]) );
        super.Exec(FormulaireSQLs.class).updateRessources(item.getId(),item.getRessources().toArray(new String[item.getRessources().size()]) );
    }

    @Override
    public void delete(UUID id){
        super.Exec(FormulaireActiviteSQLs.class).deleteByForm( id );
        super.Exec(FormulaireSQLs.class).delete(id);
    }

    @RegisterMapper(FormulaireMapper.class)
    interface FormulaireSQLs {
        @SqlQuery("select * from formulaires")
        List<FormulaireDTO> list();

        @SqlQuery("select * from formulaires where form_id = :form_id")
        FormulaireDTO getById(@Bind("form_id") UUID p_id);

        @SqlQuery("select * from formulaires where citoyen_id = :citoyenId")
        List<FormulaireDTO> getByCitoyenId(@Bind("citoyenId") UUID citoyenId);

        @SqlQuery("insert into formulaires (citoyen_id,langue,carte,visa) values(:citoyenId,:langue,:carte,:visa) RETURNING form_id ")
        String insert(@BindBean FormulaireDTO form);

        @SqlUpdate("UPDATE formulaires SET langue=:langue,carte=:carte,visa=:visa WHERE form_id=:id ")
        void update(@BindBean FormulaireDTO form );

        @SqlUpdate("UPDATE formulaires SET cursus_ac=:array WHERE form_id=:id")
        void updateCursusAc( @Bind("id") UUID id, @Bind("array") String[] array);
        @SqlUpdate("UPDATE formulaires SET ex_pro=:array WHERE form_id=:id")
        void updateExpPro( @Bind("id") UUID id, @Bind("array") String[] array);
        @SqlUpdate("UPDATE formulaires SET ressources=:array WHERE form_id=:id")
        void updateRessources( @Bind("id") UUID id, @Bind("array") String[] array);

        @SqlUpdate("DELETE from formulaires where form_id = :form_id")
        void delete(@Bind("form_id") UUID p_id);
    }

    @RegisterMapper(FormulaireActiviteMapper.class)
    interface FormulaireActiviteSQLs {
        @SqlQuery("select * from form_acivite WHERE form_id:form_id ")
        List<ActiviteDTO> listbyFormulaire(@Bind("form_id") UUID formId );

        @SqlQuery("insert into form_activite (activite_id,form_id) values(:activite_id,:form_id) RETURNING form_activite_id ")
        String insert(@Bind("activite_id") UUID activiteId,@Bind("form_id") UUID formId );

        void update(FormulaireDTO form);
        void delete(UUID id);

        @SqlUpdate("DELETE FROM form_activite WHERE form_id=:formId")
        void deleteByForm(@Bind("formId") UUID formId);
    }
    public static class FormulaireActiviteMapper implements ResultSetMapper<Map<UUID,UUID>>{
        Map<UUID,UUID> formAct;
        @Override
        public Map<UUID,UUID> map(final int i, final ResultSet r, final StatementContext statementContext) throws SQLException {
            formAct =new HashMap<>();
            formAct.put( (UUID)r.getObject("activity_id") , (UUID)r.getObject("form_id") );
            return formAct;
        }
    }

    public static class FormulaireMapper implements ResultSetMapper<FormulaireDTO> {
        FormulaireDTO formulaireDTO;
        @Override
        public FormulaireDTO map(final int i, final ResultSet r, final StatementContext statementContext) throws SQLException {

            formulaireDTO = new FormulaireDTO();
            formulaireDTO.setId((UUID) r.getObject("form_id"));
            formulaireDTO.setCitoyenId((UUID) r.getObject("citoyen_id"));
            formulaireDTO.setDateDemande((Timestamp) r.getObject("date_demande"));
            if(r.getArray("cursus_ac")!=null)
                formulaireDTO.setCursusAc( Arrays.asList((String[]) r.getArray("cursus_ac").getArray()) );
            //else
            //    formulaireDTO.setCursusAc( Arrays.asList((String[]) new ArrayList<String>().toArray() ) );
            if(r.getArray("ex_pro")!=null)
                formulaireDTO.setExpPro( Arrays.asList((String[]) r.getArray("ex_pro").getArray()) );
            //else
            //    formulaireDTO.setExpPro( Arrays.asList((String[]) new ArrayList<String>().toArray() ) );
            if(r.getArray("ressources")!=null)
                formulaireDTO.setRessources( Arrays.asList((String[]) r.getArray("ressources").getArray()) );
            //else
            //    formulaireDTO.setRessources( Arrays.asList((String[]) new ArrayList<String>().toArray() ) );
            formulaireDTO.setLangue( r.getString("langue"));
            formulaireDTO.setCarte( r.getBoolean("carte"));
            formulaireDTO.setVisa( r.getBoolean("visa"));

            return formulaireDTO;
        }
    }
}


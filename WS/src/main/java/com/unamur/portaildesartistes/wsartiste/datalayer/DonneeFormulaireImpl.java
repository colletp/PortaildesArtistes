package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.ActiviteDTO;
import com.unamur.portaildesartistes.DTO.FormulaireDTO;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class DonneeFormulaireImpl extends Donnee<FormulaireDTO>{
    private static final Logger logger = LoggerFactory.getLogger(DonneeFormulaireImpl.class);

	@Autowired
	DonneeActiviteImpl actImpl;
	
    public List<FormulaireDTO> getByCitoyenId(UUID p_id){ return super.Exec(FormulaireSQLs.class).getByCitoyenId( p_id ); }

    @Override
    public List<FormulaireDTO> list(){ return super.Exec(FormulaireSQLs.class).list(); }

    public List<FormulaireDTO> listByLangNoTrt(String lang){ return super.Exec(FormulaireSQLs.class).listByLangNoTrt(lang); }
    public List<FormulaireDTO> listByLangTrtNotDone(String lang){ return super.Exec(FormulaireSQLs.class).listByLangTrtNotDone(lang); }
    public List<FormulaireDTO> listByLangTrtDone(String lang){ return super.Exec(FormulaireSQLs.class).listByLangTrtDone(lang); }
    public List<FormulaireDTO> listByCitoyenNotValid(UUID citId){ return super.Exec(FormulaireSQLs.class).listByCitoyenNotValid(citId); }

    @Override
    public FormulaireDTO getById(UUID p_id){ return super.Exec(FormulaireSQLs.class).getById( p_id ); }
    @Override
    public UUID insert(FormulaireDTO item){
        item.setId( UUID.fromString(super.Exec(FormulaireSQLs.class).insert(item)) );
        updateFormDetails(item);
        return item.getId();
    }
    public UUID insertFormAct(UUID actId,UUID formId){
        return UUID.fromString(super.Exec(FormulaireActiviteSQLs.class).insert(actId,formId));
    }

    @Override
    public void update(FormulaireDTO item){
        super.Exec(FormulaireSQLs.class).update(item);
        updateFormDetails(item);
    }
    private void updateFormDetails(FormulaireDTO item){
        //for( UUID actId : item.getActivitesId() )
        //    super.Exec(FormulaireActiviteSQLs.class).insert(actId,item.getId());
        super.Exec(FormulaireSQLs.class).updateCursusAc  (item.getId(),item.getCursusAc()  .toArray(new String[item.getCursusAc()  .size()]) );
        super.Exec(FormulaireSQLs.class).updateExpPro    (item.getId(),item.getExpPro()    .toArray(new String[item.getExpPro()    .size()]) );
        super.Exec(FormulaireSQLs.class).updateRessources(item.getId(),item.getRessources().toArray(new String[item.getRessources().size()]) );
    }
    public void invalidate(UUID formId){
        super.Exec(FormulaireSQLs.class).invalidate(formId);
    }

    @Override
    public void delete(UUID id){
        super.Exec(FormulaireActiviteSQLs.class).deleteByForm( id );
        actImpl.deleteByForm(id);
		super.Exec(FormulaireSQLs.class).delete(id);
    }
    public void deleteAct(UUID actId){
        super.Exec(FormulaireActiviteSQLs.class).deleteAct(actId);
    }

    @RegisterMapper(FormulaireMapper.class)
    interface FormulaireSQLs {
        @SqlQuery("select * from formulaires")
        List<FormulaireDTO> list();

        @SqlQuery("select * from formulaires f WHERE f.langue=:lang AND NOT EXISTS (SELECT 1 FROM traitements t WHERE t.form_id=f.form_id) AND a_traiter=true ")
        List<FormulaireDTO> listByLangNoTrt(@Bind("lang") String lang);
        @SqlQuery("select * from formulaires f WHERE f.langue=:lang AND EXISTS ( SELECT 1 FROM traitements t WHERE t.form_id=f.form_id AND NOT EXISTS (SELECT 1 FROM reponse r WHERE r.trt_id=t.trt_id ) ) AND a_traiter=true ")
        List<FormulaireDTO> listByLangTrtNotDone(@Bind("lang") String lang);
        @SqlQuery("select * from formulaires f WHERE f.langue=:lang AND EXISTS ( SELECT 1 FROM traitements t JOIN reponse r ON r.trt_id=t.trt_id WHERE t.form_id=f.form_id ) AND a_traiter=true ")
        List<FormulaireDTO> listByLangTrtDone(@Bind("lang") String lang);
        @SqlQuery("select * from formulaires where citoyen_id = :citoyenId")
        List<FormulaireDTO> getByCitoyenId(@Bind("citoyenId") UUID citoyenId);
        @SqlQuery("select * from formulaires where citoyen_id = :citoyenId AND a_traiter=false ")
        List<FormulaireDTO> listByCitoyenNotValid(@Bind("citoyenId") UUID citoyenId);

        @SqlQuery("select * from formulaires where form_id = :form_id")
        FormulaireDTO getById(@Bind("form_id") UUID p_id);

        @SqlQuery("insert into formulaires (citoyen_id,langue,carte,visa) values(:citoyenId,:langue,:carte,:visa) RETURNING form_id ")
        String insert(@BindBean FormulaireDTO form);

        @SqlUpdate("UPDATE formulaires SET langue=:langue,carte=:carte,visa=:visa,a_traiter=true WHERE form_id=:id ")
        void update(@BindBean FormulaireDTO form );

        @SqlUpdate("UPDATE formulaires SET a_traiter=false WHERE form_id=:formId ")
        void invalidate(@Bind("formId") UUID formId );

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

        //void update(FormulaireDTO form);

        @SqlUpdate("DELETE FROM form_activite WHERE form_activite_id=:id ")
        void delete(@Bind("id") UUID id);
        @SqlUpdate("DELETE FROM form_activite WHERE activite_id=:actId ")
        void deleteAct(@Bind("actId")UUID actId);

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
            formulaireDTO.setDateDemande( r.getDate("date_demande") );
            if(r.getArray("cursus_ac")!=null)
                formulaireDTO.setCursusAc( Arrays.asList((String[]) r.getArray("cursus_ac").getArray()) );
            if(r.getArray("ex_pro")!=null)
                formulaireDTO.setExpPro( Arrays.asList((String[]) r.getArray("ex_pro").getArray()) );
            if(r.getArray("ressources")!=null)
                formulaireDTO.setRessources( Arrays.asList((String[]) r.getArray("ressources").getArray()) );
            formulaireDTO.setLangue( r.getString("langue"));
            formulaireDTO.setCarte( r.getBoolean("carte"));
            formulaireDTO.setVisa( r.getBoolean("visa"));
            formulaireDTO.setATraiter( r.getBoolean("a_traiter"));

            return formulaireDTO;
        }
    }
}


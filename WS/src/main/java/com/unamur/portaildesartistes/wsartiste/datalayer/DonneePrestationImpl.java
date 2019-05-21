package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.DTO;
import com.unamur.portaildesartistes.DTO.PrestationDTO;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
public class DonneePrestationImpl extends Donnee<PrestationDTO>{
    private static final Logger logger = LoggerFactory.getLogger(DonneePrestationImpl.class);

    @Override
    public List<PrestationDTO> list(){
        return super.Exec(PrestationSQLs.class).list();
    }
    @Override
    public PrestationDTO getById(UUID id) {
        return super.Exec(PrestationSQLs.class).getById(id);
    }
    @Override
    public UUID insert(PrestationDTO item){
        return UUID.fromString(super.Exec(PrestationSQLs.class).insert(item));
    }
    @Override
    public void update(PrestationDTO item) {
        super.Exec(PrestationSQLs.class).update(item);
    }
    @Override
    public void delete(UUID id) {
        super.Exec(PrestationSQLs.class).delete(id);
    }

    public List<PrestationDTO> listByTypeId(DTO searchType, UUID p_id) {
        switch(searchType.getClass().getName()){
            case "DocArtisteDTO" :
                return super.Exec(PrestationSQLs.class).getByDocId( p_id );
            case "CommanditaireDTO" :
                return super.Exec(PrestationSQLs.class).getByComId( p_id );
            case "ActiviteDTO" :
                return super.Exec(PrestationSQLs.class).getByActId( p_id );
            case "AdresseDTO" :
                return super.Exec(PrestationSQLs.class).getByPlaceId( p_id );
            default:
                return null;
        }
    }

    public <U extends java.lang.Class<DTO>> List<PrestationDTO> listByClassId(U searchType, UUID p_id) {
        switch(searchType.getClass().getName()){
            case "DocArtisteDTO" :
                return super.Exec(PrestationSQLs.class).getByDocId( p_id );
            case "CommanditaireDTO" :
                return super.Exec(PrestationSQLs.class).getByComId( p_id );
            case "ActiviteDTO" :
                return super.Exec(PrestationSQLs.class).getByActId( p_id );
            case "AdresseDTO" :
                return super.Exec(PrestationSQLs.class).getByPlaceId( p_id );
            default:
                return null;
        }
    }

    @RegisterMapper(PrestationMapper.class)
    interface PrestationSQLs {
        @SqlQuery("select * from prestations ")
        List<PrestationDTO> list();

        @SqlQuery("select * from prestations WHERE doc_artiste_id=:p_id ")
        List<PrestationDTO> getByDocId(@Bind("p_id") UUID p_id);

        @SqlQuery("select * from prestations WHERE commanditaire_id=:p_id ")
        List<PrestationDTO> getByComId(@Bind("p_id") UUID p_id);

        @SqlQuery("select * from prestations WHERE activite_id=:p_id ")
        List<PrestationDTO> getByActId(@Bind("p_id") UUID p_id);

        @SqlQuery("select * from prestations WHERE se_deroule_id=:p_id ")
        List<PrestationDTO> getByPlaceId(@Bind("p_id") UUID p_id);

        @SqlQuery("INSERT INTO prestations (date_prest,duree,montant,etat,commanditaire_id,doc_artiste_id,activite_id,se_deroule_id) VALUES (:datePrest,:duree,:montant,:etat,:commanditaireId,:docArtisteId,:activiteId,:seDerouleId) RETURNING prest_id ")
        String insert(@BindBean PrestationDTO test);

        PrestationDTO getById(UUID id);
        void update(PrestationDTO prest);
        void delete(UUID id);
    }

    public static class PrestationMapper implements ResultSetMapper<PrestationDTO> {
        PrestationDTO prestDTO;
        public PrestationDTO map(final int i, final ResultSet r, final StatementContext statementContext) throws SQLException {
            prestDTO = new PrestationDTO();
            prestDTO.setId((UUID) r.getObject("prest_id"));
            prestDTO.setDatePrest(r.getDate("date_prest"));
            prestDTO.setDuree(r.getInt("duree"));
            prestDTO.setMontant( r.getBigDecimal("montant").doubleValue() );
            prestDTO.setEtat( r.getString("etat") );
            prestDTO.setCommanditaireId( (UUID) r.getObject("commanditaire_id"));
            prestDTO.setDocArtisteId( (UUID) r.getObject("doc_artiste_id"));
            prestDTO.setActiviteId((UUID) r.getObject("activite_id"));
            prestDTO.setSeDerouleId((UUID) r.getObject("se_deroule_id"));
            return prestDTO;
        }
    }
}
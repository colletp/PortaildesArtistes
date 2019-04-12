package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.PrestationDTO;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
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
import java.util.List;
import java.util.UUID;

@Repository
public class DonneePrestationImpl implements DonneePrestation{
    private static final Logger logger = LoggerFactory.getLogger(DonneePrestationImpl.class);

    @Autowired
    private DBI dbiBean;

    public List<PrestationDTO> list(){
        Handle handle = dbiBean.open();
        PrestationSQLs PrestationSQLs = handle.attach(PrestationSQLs.class);
        return PrestationSQLs.list();
    }

    public UUID insert(PrestationDTO item){
        Handle handle = dbiBean.open();
        PrestationSQLs PrestationSQLs = handle.attach(PrestationSQLs.class);
        return PrestationSQLs.insert(item);
    }

    public List<PrestationDTO> getByDocId(UUID p_id){
        Handle handle = dbiBean.open();
        PrestationSQLs PrestationSQLs = handle.attach(PrestationSQLs.class);
        return PrestationSQLs.getByDocId( p_id );
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

        @SqlUpdate("INSERT INTO prestations (date_prest,duree,montant,etat,commanditaire_id,doc_artiste_id,activite_id,se_deroule_id) VALUES (:date_prest,:duree,:montant,:etat,:commanditaire_id,:doc_artiste_id,:activite_id,:se_deroule_id) ")
        @GetGeneratedKeys
        UUID insert(@BindBean PrestationDTO test);
    }

    public static class PrestationMapper implements ResultSetMapper<PrestationDTO> {
        PrestationDTO prestDTO;
        public PrestationDTO map(final int i, final ResultSet r, final StatementContext statementContext) throws SQLException {
            prestDTO = new PrestationDTO();
            prestDTO.setId((UUID) r.getObject("prest_id"));
            prestDTO.setDatePrest(r.getTimestamp("date_prest"));
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
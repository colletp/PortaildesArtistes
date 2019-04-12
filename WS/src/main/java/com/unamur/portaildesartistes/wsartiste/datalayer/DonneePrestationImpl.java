package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.PrestationDTO;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
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

    @RegisterMapper(PrestationMapper.class)
    interface PrestationSQLs {
        @SqlQuery("select * from prestations ")
        List<PrestationDTO> list();

        @SqlUpdate("INSERT INTO prestations (nom_Prestation) VALUES (:nomPrestation) ")
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
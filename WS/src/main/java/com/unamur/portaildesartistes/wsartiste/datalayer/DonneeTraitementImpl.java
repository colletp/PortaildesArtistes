package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.TraitementDTO;
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
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Repository
public class DonneeTraitementImpl implements DonneeTraitement{
    private static final Logger logger = LoggerFactory.getLogger(DonneeTraitementImpl.class);

    @Autowired
    private DBI dbiBean;

    public List<TraitementDTO> list(){
        Handle handle = dbiBean.open();
        TraitementSQLs TraitementSQLs = handle.attach(TraitementSQLs.class);
        return TraitementSQLs.list();
    }

    public UUID insert(TraitementDTO item){
        Handle handle = dbiBean.open();
        TraitementSQLs TraitementSQLs = handle.attach(TraitementSQLs.class);
        return TraitementSQLs.insert(item);
    }

    @RegisterMapper(TraitementMapper.class)
    interface TraitementSQLs {
        @SqlQuery("select * from traitements ")
        List<TraitementDTO> list();

        @SqlUpdate("INSERT INTO traitements (date_trt,appreciation,roles_id,gest_id,form_id,citoyen_prest_id,type_role) VALUES (:date_trt,:appreciation,:roles_id,:gest_id,:form_id,:citoyen_prest_id,:type_role) ")
        @GetGeneratedKeys
        UUID insert(@BindBean TraitementDTO test);
    }

    public static class TraitementMapper implements ResultSetMapper<TraitementDTO> {
        TraitementDTO trtDTO;
        public TraitementDTO map(final int i, final ResultSet r, final StatementContext statementContext) throws SQLException {
            trtDTO = new TraitementDTO();
            trtDTO.setId((UUID) r.getObject("trt_id"));

            trtDTO.setDateTrt( r.getTimestamp("date_trt"));
            trtDTO.setAppreciation( r.getString("appreciation"));
            trtDTO.setRoleId( (UUID) r.getObject("roles_id"));
            trtDTO.setGestId( (UUID) r.getObject("gest_id"));
            trtDTO.setFormId( (UUID) r.getObject("form_id"));
            trtDTO.setCitoyenPrestId( (UUID) r.getObject("citoyen_prest_id"));
            trtDTO.setTypeRole( r.getString("type_role"));
            return trtDTO;
        }
    }
}
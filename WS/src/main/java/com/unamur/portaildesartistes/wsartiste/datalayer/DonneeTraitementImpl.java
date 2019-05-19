package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.TraitementDTO;
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
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Repository
public class DonneeTraitementImpl extends Donnee<TraitementDTO>{
    private static final Logger logger = LoggerFactory.getLogger(DonneeTraitementImpl.class);

    @Override
    public List<TraitementDTO> list(){
        return super.Exec(TraitementSQLs.class).list();
    }

    public List<TraitementDTO> listByLang(String lang){
        return super.Exec(TraitementSQLs.class).listByLang( lang );
    }

    @Override
    public TraitementDTO getById(UUID id) {
        return super.Exec(TraitementSQLs.class).getById(id);
    }

    public UUID insert(TraitementDTO item){
        return UUID.fromString(super.Exec(TraitementSQLs.class).insert(item));
    }

    @Override
    public void update(TraitementDTO item) {
        super.Exec(TraitementSQLs.class).update(item);
    }

    @Override
    public void delete(UUID id) {
        super.Exec(TraitementSQLs.class).delete(id);
    }

    @RegisterMapper(TraitementMapper.class)
    interface TraitementSQLs {
        @SqlQuery("select * from traitements ")
        List<TraitementDTO> list();

        @SqlQuery("select t.* from traitements t JOIN formulaires f ON t.form_id=f.form_id WHERE f.langue=:lang ")
        List<TraitementDTO> listByLang( @Bind("lang") String lang);

        @SqlQuery("INSERT INTO traitements (date_trt,appreciation,roles_id,gest_id,form_id,citoyen_prest_id,type_role) VALUES (:date_trt,:appreciation,:roles_id,:gest_id,:form_id,:citoyen_prest_id,:type_role) RETURNING trt_id ")
        String insert(@BindBean TraitementDTO test);

        @SqlQuery("select * from traitements WHERE trt_id=:id ")
        TraitementDTO getById(@Bind("id") UUID id);

        @SqlUpdate("UPDATE traitements SET date_trt,appreciation,gest_id) VALUES (:date_trt,:appreciation,:gest_id) WHERE trt_id=:id ")
        void update(TraitementDTO trt);

        @SqlUpdate("DELETE from traitements WHERE trt_id=:id ")
        void delete(@Bind("id") UUID id);
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
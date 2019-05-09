package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.ReponseDTO;
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
public class DonneeReponseImpl extends Donnee<ReponseDTO>{
    private static final Logger logger = LoggerFactory.getLogger(DonneeReponseImpl.class);

    @Override
    public List<ReponseDTO> list(){
        return super.Exec(ReponseSQLs.class).list();
    }

    @Override
    public ReponseDTO getById(UUID id) {
        return super.Exec(ReponseSQLs.class).getById(id);
    }

    @Override
    public UUID insert(ReponseDTO item){
        return UUID.fromString(super.Exec(ReponseSQLs.class).insert(item));
    }

    @Override
    public void update(ReponseDTO item) {
        super.Exec(ReponseSQLs.class).update(item);
    }

    @Override
    public void delete(UUID id) {
        super.Exec(ReponseSQLs.class).delete(id);
    }

    @RegisterMapper(ReponseMapper.class)
    interface ReponseSQLs {
        @SqlQuery("select * from reponse ")
        List<ReponseDTO> list();

        @SqlUpdate("INSERT INTO Reponse (trt_id,citoyen_id,date_reponse) VALUES (:trtId,:citoyenId,:dateReponse) RETURNING reponse_id ")
        String insert(@BindBean ReponseDTO test);

        ReponseDTO getById(UUID id);
        void update(ReponseDTO rep);
        void delete(UUID id);
    }

    public static class ReponseMapper implements ResultSetMapper<ReponseDTO> {
        ReponseDTO repDTO;
        public ReponseDTO map(final int i, final ResultSet r, final StatementContext statementContext) throws SQLException {
            repDTO = new ReponseDTO();
            repDTO.setId((UUID) r.getObject("reponse_id"));
            repDTO.setTrtId((UUID) r.getObject("trt_id"));
            repDTO.setCitoyenId((UUID) r.getObject("citoyen_id"));
            repDTO.setDateReponse( r.getTimestamp("date_reponse"));

            return repDTO;
        }
    }
}
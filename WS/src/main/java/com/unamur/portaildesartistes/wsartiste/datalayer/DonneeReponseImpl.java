package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.ReponseDTO;
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
public class DonneeReponseImpl implements DonneeReponse{
    private static final Logger logger = LoggerFactory.getLogger(DonneeReponseImpl.class);

    @Autowired
    private DBI dbiBean;

    public List<ReponseDTO> list(){
        Handle handle = dbiBean.open();
        ReponseSQLs ReponseSQLs = handle.attach(ReponseSQLs.class);
        return ReponseSQLs.list();
    }

    public UUID insert(ReponseDTO item){
        Handle handle = dbiBean.open();
        ReponseSQLs ReponseSQLs = handle.attach(ReponseSQLs.class);
        return ReponseSQLs.insert(item);
    }

    @RegisterMapper(ReponseMapper.class)
    interface ReponseSQLs {
        @SqlQuery("select * from reponse ")
        List<ReponseDTO> list();

        @SqlUpdate("INSERT INTO Reponse (trt_id,citoyen_id,date_reponse) VALUES (:trtId,:citoyenId,:dateReponse) ")
        @GetGeneratedKeys
        UUID insert(@BindBean ReponseDTO test);
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
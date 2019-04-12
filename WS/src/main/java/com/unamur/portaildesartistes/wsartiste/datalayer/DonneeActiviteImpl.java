package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.ActiviteDTO;
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
public class DonneeActiviteImpl implements DonneeActivite{
    private static final Logger logger = LoggerFactory.getLogger(DonneeCitoyenImpl.class);

    @Autowired
    private DBI dbiBean;

    public List<ActiviteDTO> list(){
        Handle handle = dbiBean.open();
        ActiviteSQLs ActiviteSQLs = handle.attach(ActiviteSQLs.class);
        return ActiviteSQLs.list();
    }

    public UUID insert(ActiviteDTO item){
        Handle handle = dbiBean.open();
        ActiviteSQLs ActiviteSQLs = handle.attach(ActiviteSQLs.class);
        return ActiviteSQLs.insert(item);
    }

    @RegisterMapper(SecteurMapper.class)
    interface ActiviteSQLs {
        @SqlQuery("select * from activite ")
        List<ActiviteDTO> list();

        @SqlUpdate("INSERT INTO activite (nom_activite,secteur_id) VALUES (:nomActivite,:idSecteur) ")
        @GetGeneratedKeys
        UUID insert(@BindBean ActiviteDTO test);
    }

    public static class SecteurMapper implements ResultSetMapper<ActiviteDTO> {
        ActiviteDTO activiteDTO;
        public ActiviteDTO map(final int i, final ResultSet r, final StatementContext statementContext) throws SQLException {
            activiteDTO = new ActiviteDTO();
            activiteDTO.setId((UUID) r.getObject("activite_id"));
            activiteDTO.setIdSecteur((UUID) r.getObject("secteur_id"));
            activiteDTO.setNomActivite(r.getString("nom_activite"));
            return activiteDTO;
        }
    }
}
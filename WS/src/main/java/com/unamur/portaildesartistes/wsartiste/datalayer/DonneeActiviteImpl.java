package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.ActiviteDTO;
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
    public ActiviteDTO getById(UUID p_id){
        Handle handle = dbiBean.open();
        ActiviteSQLs ActiviteSQLs = handle.attach(ActiviteSQLs.class);
        return ActiviteSQLs.getById(p_id);
    }
    public List<ActiviteDTO> getByDocId(UUID p_id){
        Handle handle = dbiBean.open();
        ActiviteSQLs ActiviteSQLs = handle.attach(ActiviteSQLs.class);
        return ActiviteSQLs.getByDocId(p_id);
    }

    public List<ActiviteDTO> getByFormId(UUID p_id){
        Handle handle = dbiBean.open();
        ActiviteSQLs ActiviteSQLs = handle.attach(ActiviteSQLs.class);
        return ActiviteSQLs.getByFormId(p_id);
    }

    @RegisterMapper(SecteurMapper.class)
    interface ActiviteSQLs {
        @SqlQuery("select * from activite ")
        List<ActiviteDTO> list();

        @SqlQuery("select * from activite a WHERE a.activite_id = :p_id ")
        ActiviteDTO getById(@Bind("p_id")UUID p_id);

        @SqlQuery("select * from activite a JOIN doc_artiste_activite da ON a.activite_id = da.activite_id WHERE da.doc_artiste_id=:docId ")
        List<ActiviteDTO> getByDocId(@Bind("docId")UUID docId);

        @SqlQuery("select * from activite a JOIN form_activite fa ON a.activite_id = fa.activite_id WHERE fa.form_id=:formId ")
        List<ActiviteDTO> getByFormId(@Bind("formId")UUID formId);

        @SqlUpdate("INSERT INTO activite (nom_activite,secteur_id) VALUES (:nomActivite,:idSecteur) ")
        @GetGeneratedKeys
        UUID insert(@BindBean ActiviteDTO test);
    }

    public static class SecteurMapper implements ResultSetMapper<ActiviteDTO> {
        ActiviteDTO activiteDTO;
        public ActiviteDTO map(final int i, final ResultSet r, final StatementContext statementContext) throws SQLException {
            activiteDTO = new ActiviteDTO();
            activiteDTO.setId((UUID) r.getObject("activite_id"));
            activiteDTO.setSecteurId((UUID) r.getObject("secteur_id"));
            activiteDTO.setNomActivite(r.getString("nom_activite"));
            return activiteDTO;
        }
    }
}
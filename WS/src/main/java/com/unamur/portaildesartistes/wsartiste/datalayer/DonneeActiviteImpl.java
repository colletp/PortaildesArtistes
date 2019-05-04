package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.ActiviteDTO;
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
public class DonneeActiviteImpl extends Donnee<ActiviteDTO>{
    private static final Logger logger = LoggerFactory.getLogger(DonneeCitoyenImpl.class);

    public List<ActiviteDTO> list(){
        return super.Exec(ActiviteSQLs.class).list();
    }
    public UUID insert(ActiviteDTO item){
        return UUID.fromString(super.Exec(ActiviteSQLs.class).insert(item));
    }
    public void update(ActiviteDTO item) {
        super.Exec(ActiviteSQLs.class).update(item);
    }
    public void delete(UUID id) {
        super.Exec(ActiviteSQLs.class).delete(id);
    }

    public ActiviteDTO getById(UUID p_id){
        return super.Exec(ActiviteSQLs.class).getById(p_id);
    }
    public List<ActiviteDTO> getByDocId(UUID p_id){
        return super.Exec(ActiviteSQLs.class).getByDocId(p_id);
    }

    public List<ActiviteDTO> getByFormId(UUID p_id){
        return super.Exec(ActiviteSQLs.class).getByFormId(p_id);
    }

    public List<ActiviteDTO> getBySecteurId(UUID p_id){
        return super.Exec(ActiviteSQLs.class).getBySecteurId(p_id);
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

        @SqlQuery("select * from activite a JOIN secteur s ON a.secteur_id = s.secteur_id WHERE s.secteur_id=:formId ")
        List<ActiviteDTO> getBySecteurId(@Bind("formId")UUID formId);

        @SqlQuery("INSERT INTO activite (nom_activite,secteur_id) VALUES (:nomActivite,:idSecteur) RETURNING activite_id ")
        String insert(@BindBean ActiviteDTO test);

        @SqlUpdate("UPDATE activite SET nom_activite=:nomActivite WHERE activite_id=:id ")
        void update(@BindBean ActiviteDTO act);

        void delete(UUID id);
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
package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.SecteurDTO;
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
public class DonneeSecteurImpl extends Donnee<SecteurDTO>{
    private static final Logger logger = LoggerFactory.getLogger(DonneeCitoyenImpl.class);
    @Override
    public List<SecteurDTO> list(){
        return super.Exec(SecteurSQLs.class).list();
    }
    @Override
    public SecteurDTO getById(UUID p_id){
        return super.Exec(SecteurSQLs.class).getById(p_id);
    }
    @Override
    public UUID insert(SecteurDTO item){
        return UUID.fromString(super.Exec(SecteurSQLs.class).insert(item));
    }
    @Override
    public void update(SecteurDTO item) {
        super.Exec(SecteurSQLs.class).update(item);
    }
    @Override
    public void delete(UUID id) {
        super.Exec(SecteurSQLs.class).delete(id);
    }

    public List<SecteurDTO> getByDocId(UUID p_id){
        return super.Exec(SecteurSQLs.class).getByDocId(p_id);
    }
    public List<SecteurDTO> getByFormId(UUID p_id){
        return super.Exec(SecteurSQLs.class).getByFormId(p_id);
    }

    @RegisterMapper(SecteurMapper.class)
    interface SecteurSQLs {
        @SqlQuery("select * from secteur ")
        List<SecteurDTO> list();
        @SqlQuery("select * from secteur WHERE secteur_id = :p_id ")
        SecteurDTO getById(@Bind("p_id") UUID p_id);
        @SqlQuery("INSERT INTO secteur (nom_secteur) VALUES (:nomSecteur) ")
        String insert(@BindBean SecteurDTO test);

        @SqlQuery("select distinct s.* from secteur s JOIN activite a ON s.secteur_id=a.secteur_id JOIN doc_artiste_activite da ON a.activite_id = da.activite_id WHERE da.doc_artiste_id=:docId ")
        List<SecteurDTO> getByDocId(@Bind("docId")UUID docId);
        @SqlQuery("select distinct s.* from secteur s JOIN activite a ON s.secteur_id=a.secteur_id JOIN form_activite fa ON a.activite_id = fa.activite_id WHERE fa.form_id=:formId ")
        List<SecteurDTO> getByFormId(@Bind("formId")UUID formId);


        void update(SecteurDTO sec);
        void delete(UUID id);
    }

    public static class SecteurMapper implements ResultSetMapper<SecteurDTO> {
        SecteurDTO sectDTO;
        public SecteurDTO map(final int i, final ResultSet r, final StatementContext statementContext) throws SQLException {
            sectDTO = new SecteurDTO();
            sectDTO.setId((UUID) r.getObject("secteur_id"));
            sectDTO.setNomSecteur(r.getString("nom_secteur"));
            return sectDTO;
        }
    }
}
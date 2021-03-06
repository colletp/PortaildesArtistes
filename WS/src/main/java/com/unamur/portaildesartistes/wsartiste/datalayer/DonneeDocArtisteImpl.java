package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.DocArtisteDTO;
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
public class DonneeDocArtisteImpl extends Donnee<DocArtisteDTO>{
    private static final Logger logger = LoggerFactory.getLogger(DonneeDocArtisteImpl.class);

    public List<DocArtisteDTO> getByReponse(UUID repId){ return super.Exec(DocArtisteSQLs.class).getByReponse(repId); }
    public List<DocArtisteDTO> listByLang(String lang){ return super.Exec(DocArtisteSQLs.class).listByLang(lang); }

    @Override
    public List<DocArtisteDTO> list(){
        return super.Exec(DocArtisteSQLs.class).list();
    }

    @Override
    public DocArtisteDTO getById(UUID id) {
        return super.Exec(DocArtisteSQLs.class).getById(id);
    }

    public UUID insert(DocArtisteDTO item){
        return UUID.fromString(super.Exec(DocArtisteSQLs.class).insert(item));
    }

    @Override
    public void update(DocArtisteDTO item) {
        super.Exec(DocArtisteSQLs.class).update(item);
    }

    @Override
    public void delete(UUID id) {
        super.Exec(DocArtisteSQLs.class).delete(id);
    }

    public List<DocArtisteDTO> getByCitoyenId(UUID p_id){
        return super.Exec(DocArtisteSQLs.class).getByCitoyenId(p_id);
    }

    @RegisterMapper(DocArtisteMapper.class)
    interface DocArtisteSQLs {

        @SqlQuery("select * from doc_artiste WHERE reponse_id=:reponse_id ")
        List<DocArtisteDTO> getByReponse(@Bind("reponse_id") UUID repId );

        @SqlQuery("select * from doc_artiste ")
        List<DocArtisteDTO> list();

        @SqlQuery("SELECT * FROM doc_artiste d " +
                "JOIN reponse r ON r.reponse_id=d.reponse_id " +
                "JOIN traitements t ON t.trt_id = r.trt_id " +
                "JOIN formulaires f ON f.form_id=t.form_id AND f.langue=:lang ")
        List<DocArtisteDTO> listByLang(@Bind("lang") String lang);

        //@SqlQuery("select * from doc_artiste WHERE citoyen_id=:citoyenId ")
        @SqlQuery("SELECT * FROM doc_artiste d " +
                "JOIN reponse r ON r.reponse_id=d.reponse_id " +
                "JOIN traitements t ON t.trt_id = r.trt_id " +
                "JOIN formulaires f ON f.form_id=t.form_id AND f.citoyen_id=:citoyenId ")
        List<DocArtisteDTO> getByCitoyenId(@Bind("citoyenId") UUID citoyenId);

        @SqlQuery("INSERT INTO doc_artiste (reponse_id,no_doc,date_peremption,type_doc_artiste) VALUES (:reponseId,:noDoc,:datePeremption,:typeDocArtiste) RETURNING doc_artiste_id ")
        String insert(@BindBean DocArtisteDTO test);

        @SqlQuery("select * from doc_artiste WHERE doc_artiste_id=:p_id ")
        DocArtisteDTO getById( @Bind("p_id") UUID p_id );

        //pas d'update, une fois créé il ne doit plus être modifié ni supprimé
        void update(DocArtisteDTO doc);
        void delete(UUID id);
    }

    public static class DocArtisteMapper implements ResultSetMapper<DocArtisteDTO> {
        DocArtisteDTO docArtisteDTO;
        public DocArtisteDTO map(final int i, final ResultSet r, final StatementContext statementContext) throws SQLException {
            docArtisteDTO = new DocArtisteDTO();
            docArtisteDTO.setId((UUID) r.getObject("doc_artiste_id"));
            docArtisteDTO.setReponseId((UUID) r.getObject("reponse_id"));
            docArtisteDTO.setNoDoc(r.getString("no_doc"));
            docArtisteDTO.setDatePeremption(r.getDate("date_peremption"));
            docArtisteDTO.setTypeDocArtiste(r.getString("type_doc_artiste"));
            return docArtisteDTO;
        }
    }
}
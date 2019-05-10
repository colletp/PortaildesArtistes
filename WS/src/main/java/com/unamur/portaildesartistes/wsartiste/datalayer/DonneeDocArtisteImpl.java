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
        @SqlQuery("select * from doc_artiste ")
        List<DocArtisteDTO> list();

        @SqlQuery("select * from doc_artiste WHERE citoyen_id=:citoyenId ")
        List<DocArtisteDTO> getByCitoyenId(@Bind("citoyenId") UUID citoyenId);

        @SqlQuery("INSERT INTO doc_artiste (citoyen_id,reponse_id,no_doc,nom_artiste,date_peremption,type_doc_artiste) VALUES (:citoyen_id,:reponse_id,:no_doc,:nom_artiste,:date_peremption,:type_doc_artiste) RETURNING doc_artiste_id ")
        String insert(@BindBean DocArtisteDTO test);

        @SqlQuery("select * from doc_artiste WHERE doc_artiste_id=:p_id ")
        DocArtisteDTO getById( @Bind("p_id") UUID p_id );

        //pas d'update, une fois créé il ne doit plus être modifié
        //@SqlUpdate("UPDATE doc_artiste SET no_doc,nom_artiste,date_peremption WHERE doc_artiste_id=:id")
        void update(DocArtisteDTO doc);

        void delete(UUID id);
    }

    public static class DocArtisteMapper implements ResultSetMapper<DocArtisteDTO> {
        DocArtisteDTO docArtisteDTO;
        public DocArtisteDTO map(final int i, final ResultSet r, final StatementContext statementContext) throws SQLException {
            docArtisteDTO = new DocArtisteDTO();
            docArtisteDTO.setId((UUID) r.getObject("doc_artiste_id"));
            docArtisteDTO.setCitoyenId((UUID) r.getObject("citoyen_id"));
            docArtisteDTO.setReponseId((UUID) r.getObject("reponse_id"));
            docArtisteDTO.setNoDoc(r.getString("no_doc"));
            docArtisteDTO.setNomArtiste(r.getString("nom_artiste"));
            docArtisteDTO.setDatePeremption(r.getDate("date_peremption"));
            docArtisteDTO.setTypeDocArtiste(r.getString("type_doc_artiste"));
            return docArtisteDTO;
        }
    }
}
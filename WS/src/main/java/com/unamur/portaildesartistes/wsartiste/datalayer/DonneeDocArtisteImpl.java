package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.DocArtisteDTO;
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
public class DonneeDocArtisteImpl implements DonneeDocArtiste{
    private static final Logger logger = LoggerFactory.getLogger(DonneeDocArtisteImpl.class);

    @Autowired
    private DBI dbiBean;

    public List<DocArtisteDTO> list(){
        Handle handle = dbiBean.open();
        SecteurSQLs SecteurSQLs = handle.attach(SecteurSQLs.class);
        return SecteurSQLs.list();
    }

    public UUID insert(DocArtisteDTO item){
        Handle handle = dbiBean.open();
        SecteurSQLs SecteurSQLs = handle.attach(SecteurSQLs.class);
        return SecteurSQLs.insert(item);
    }

    @RegisterMapper(DocArtisteMapper.class)
    interface SecteurSQLs {
        @SqlQuery("select * from doc_artiste ")
        List<DocArtisteDTO> list();

        @SqlUpdate("INSERT INTO doc_artiste (citoyen_id,reponse_id,no_doc,nom_artiste,date_peremption,type_doc_artiste) VALUES (:citoyen_id,:reponse_id,:no_doc,:nom_artiste,:date_peremption,:type_doc_artiste) ")
        @GetGeneratedKeys
        UUID insert(@BindBean DocArtisteDTO test);
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
            docArtisteDTO.setTypeDocArtiste(r.getString("type_nom_artiste"));
            return docArtisteDTO;
        }
    }
}
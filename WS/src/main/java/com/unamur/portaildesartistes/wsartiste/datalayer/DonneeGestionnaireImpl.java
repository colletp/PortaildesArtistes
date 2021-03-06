package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.GestionnaireDTO;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
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
public class DonneeGestionnaireImpl extends Donnee<GestionnaireDTO>{
    private static final Logger logger = LoggerFactory.getLogger(DonneeGestionnaireImpl.class);

    @Override
    public List<GestionnaireDTO> list(){
        return super.Exec(GestionnaireSQLs.class).list();
    }

    @Override
    public GestionnaireDTO getById(UUID id) {
        return super.Exec(GestionnaireSQLs.class).getById(id);
    }

    public GestionnaireDTO getByCitoyenId(UUID id){ return super.Exec(GestionnaireSQLs.class).getByCitoyenId(id); }

    @Override
    public UUID insert(GestionnaireDTO item){
        return UUID.fromString(super.Exec(GestionnaireSQLs.class).insert(item));
    }

    @Override
    public void update(GestionnaireDTO item) {
        super.Exec(GestionnaireSQLs.class).update(item);
    }

    @Override
    public void delete(UUID id) {
        super.Exec(GestionnaireSQLs.class).delete(id);
    }

    @RegisterMapper(GestionnaireMapper.class)
    interface GestionnaireSQLs {
        @SqlQuery("select * from gestionnaire ")
        List<GestionnaireDTO> list();

        @SqlQuery("INSERT INTO gestionnaire (citoyen_id,matricule,bureau,travaille) VALUES (:citoyen_id,:matricule,:bureau,:travaille) RETURNING gest_id")
        String insert(@BindBean GestionnaireDTO item);

        @SqlQuery("select * from gestionnaire WHERE gest_id=:id ")
        GestionnaireDTO getById( @Bind("id") UUID gest_id);

        @SqlQuery("select * from gestionnaire WHERE citoyen_id=:id ")
        GestionnaireDTO getByCitoyenId( @Bind("id") UUID cit_id);

        @SqlUpdate("UPDATE gestionnaire SET matricule=:matricule,bureau=:bureau WHERE gest_id=:id")
        void update(@BindBean GestionnaireDTO item);

        @SqlUpdate("DELETE FROM gestionnaire WHERE gest_id=:id")
        void delete(@Bind("id") UUID id);
    }

    public static class GestionnaireMapper implements ResultSetMapper<GestionnaireDTO> {
        GestionnaireDTO gestDTO;
        public GestionnaireDTO map(final int i, final ResultSet r, final StatementContext statementContext) throws SQLException {
            gestDTO = new GestionnaireDTO();
            gestDTO.setId((UUID) r.getObject("gest_id"));
            gestDTO.setCitoyenId((UUID) r.getObject("citoyen_id"));
            gestDTO.setTravailleId((UUID) r.getObject("travaille"));
            gestDTO.setMatricule(r.getString("matricule"));
            gestDTO.setBureau(r.getString("bureau"));
            return gestDTO;
        }
    }
}
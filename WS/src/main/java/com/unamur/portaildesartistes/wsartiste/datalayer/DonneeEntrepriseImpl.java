package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.ActiviteDTO;
import com.unamur.portaildesartistes.DTO.CitoyenDTO;
import com.unamur.portaildesartistes.DTO.EntrepriseDTO;
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
public class DonneeEntrepriseImpl extends Donnee<EntrepriseDTO>{
    private static final Logger logger = LoggerFactory.getLogger(DonneeCitoyenImpl.class);

    public List<EntrepriseDTO> list(){
        return super.Exec(EntrepriseSQLs.class).list();
    }
    public EntrepriseDTO getById(UUID p_id){
        return super.Exec(EntrepriseSQLs.class).getById(p_id);
    }

    public UUID insert(EntrepriseDTO item){
        return UUID.fromString(super.Exec(EntrepriseSQLs.class).insert(item));
    }

    @Override
    void update(EntrepriseDTO item) {
        super.Exec(EntrepriseSQLs.class).update(item);
    }

    @Override
    void delete(UUID id) {
        super.Exec(EntrepriseSQLs.class).delete(id);
    }

    @RegisterMapper(EnrepriseMapper.class)
    interface EntrepriseSQLs {
        @SqlQuery("select * from entreprise ")
        List<EntrepriseDTO> list();

        @SqlQuery("select * from entreprise WHERE entreprise_id = :p_id ")
        EntrepriseDTO getById(@Bind("p_id")UUID p_id);

        @SqlUpdate("INSERT INTO entrerise (contact_id,siege_id,bce,denomination,statut_legal) VALUES (:contact_id,:siege_id,:bce,:denomination,:statut_legal) RETURNING entreprise_id ")
        String insert(@BindBean EntrepriseDTO test);

        void update(EntrepriseDTO ent);
        void delete(UUID id);
    }

    public static class EnrepriseMapper implements ResultSetMapper<EntrepriseDTO> {
        EntrepriseDTO entrDTO;
        public EntrepriseDTO map(final int i, final ResultSet r, final StatementContext statementContext) throws SQLException {
            entrDTO = new EntrepriseDTO();
            entrDTO.setId((UUID) r.getObject("entreprise_id"));
            entrDTO.setContactId((UUID) r.getObject("contact_id"));
            entrDTO.setSiegeId((UUID) r.getObject("siege_id"));
            entrDTO.setBce(r.getString("bce"));
            entrDTO.setDenomination(r.getString("denomination"));
            entrDTO.setStatutLegal(r.getString("statut_legal"));
            return entrDTO;
        }
    }
}
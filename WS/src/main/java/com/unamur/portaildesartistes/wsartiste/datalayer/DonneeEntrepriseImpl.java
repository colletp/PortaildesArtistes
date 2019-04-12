package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.CitoyenDTO;
import com.unamur.portaildesartistes.DTO.EntrepriseDTO;
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
public class DonneeEntrepriseImpl implements DonneeEntreprise{
    private static final Logger logger = LoggerFactory.getLogger(DonneeCitoyenImpl.class);

    @Autowired
    private DBI dbiBean;

    public List<EntrepriseDTO> list(){
        Handle handle = dbiBean.open();
        EntrepriseSQLs EntrepriseSQLs = handle.attach(EntrepriseSQLs.class);
        return EntrepriseSQLs.list();
    }

    public UUID insert(EntrepriseDTO item){
        Handle handle = dbiBean.open();
        EntrepriseSQLs EntrepriseSQLs = handle.attach(EntrepriseSQLs.class);
        return EntrepriseSQLs.insert(item);
    }

    @RegisterMapper(EnrepriseMapper.class)
    interface EntrepriseSQLs {
        @SqlQuery("select * from entreprise ")
        List<EntrepriseDTO> list();

        @SqlUpdate("INSERT INTO entrerise (contact_id,siege_id,bce,denomination,statut_legal) VALUES (:contact_id,:siege_id,:bce,:denomination,:statut_legal) ")
        @GetGeneratedKeys
        UUID insert(@BindBean EntrepriseDTO test);
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
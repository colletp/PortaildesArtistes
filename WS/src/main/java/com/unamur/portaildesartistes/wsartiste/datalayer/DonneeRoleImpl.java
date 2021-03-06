package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.RoleDTO;
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
public class DonneeRoleImpl extends Donnee<RoleDTO>{
    private static final Logger logger = LoggerFactory.getLogger(DonneeCitoyenImpl.class);

    public List<RoleDTO> getByCitoyenId(UUID p_id){
        return super.Exec(RoleSQLs.class).getByCitoyenId( p_id );
    }
    public List<RoleDTO> getByCitoyenUserName(String username){
        return super.Exec(RoleSQLs.class).getByCitoyenUserName( username );
    }

    @Override
    public List<RoleDTO> list() {
        return super.Exec(RoleSQLs.class).list();
    }

    @Override
    public RoleDTO getById(UUID id) {
        return super.Exec(RoleSQLs.class).getById(id);
    }

    @Override
    public UUID insert(RoleDTO item) {
        return UUID.fromString(super.Exec(RoleSQLs.class).insert(item));
    }

    @Override
    public void update(RoleDTO item) {
        super.Exec(RoleSQLs.class).update(item);
    }

    @Override
    public void delete(UUID id) {
        super.Exec(RoleSQLs.class).delete(id);
    }


    @RegisterMapper(RoleMapper.class)
    interface RoleSQLs {
        @SqlQuery("select r.* from roles r")
        List<RoleDTO> list();

        @SqlQuery("select r.* from gestionnaire g " +
                "join gestionnaire_roles gr on g.gest_id=gr.gest_id " +
                "join roles r on gr.roles_id=r.roles_id " +
                "where g.citoyen_id=:citoyen_id")
        List<RoleDTO> getByCitoyenId(@Bind("citoyen_id")UUID p_id);

        @SqlQuery("select r.* from gestionnaire g " +
                "join gestionnaire_roles gr on g.gest_id=gr.gest_id " +
                "join roles r on gr.roles_id=r.roles_id " +
                "join citoyen c on g.citoyen_id=c.citoyen_id " +
                "where c.login=:username")
        List<RoleDTO> getByCitoyenUserName(@Bind("username")String username);

        RoleDTO getById(UUID id);

        void update(RoleDTO rol);

        void delete(UUID id);

        String insert(RoleDTO rol);
    }

    public static class RoleMapper implements ResultSetMapper<RoleDTO> {
        RoleDTO roleDTO;
        public RoleDTO map(final int i, final ResultSet r, final StatementContext statementContext) throws SQLException {
            roleDTO = new RoleDTO();
            roleDTO.setId((UUID) r.getObject("roles_id"));
            roleDTO.setNomRole(r.getString("type_roles"));
            roleDTO.setLang(r.getString("langue"));
            return roleDTO;
        }
    }
}
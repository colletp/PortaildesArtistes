package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.dtoArtiste.corelayer.RoleDTO;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
public class DonneeRoleImpl implements DonneeRole{
    private static final Logger logger = LoggerFactory.getLogger(DonneeUtilisateurImpl.class);

    @Autowired
    private DBI dbiBean;

    public List<RoleDTO> getByUser(UUID p_id){
        Handle handle = dbiBean.open();
        RoleSQLs RoleSQLs = handle.attach(RoleSQLs.class);
        return RoleSQLs.getByUser( p_id );
    }

    @RegisterMapper(RoleMapper.class)
    interface RoleSQLs {
        @SqlQuery("select r.* from gestionnaire g join gestionnaire_roles gr on g.gest_id=gr.gest_id join roles r on gr.roles_id=r.roles_id where g.citoyen_id=:citoyen_id")
        List<RoleDTO> getByUser(@Bind("citoyen_id")UUID p_id);
    }

    //@Component
    public static class RoleMapper implements ResultSetMapper<RoleDTO> {
        RoleDTO bean;
        public RoleDTO map(final int i, final ResultSet r, final StatementContext statementContext) throws SQLException {
            bean = new RoleDTO();
            bean.setId((UUID) r.getObject("roles_id"));
            bean.setNomRole(r.getString("type_roles"));
            bean.setLang(r.getString("langue"));
            return bean;
        }
    }
}
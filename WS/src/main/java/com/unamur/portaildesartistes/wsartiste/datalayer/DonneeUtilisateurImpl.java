package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.CitoyenDTO;
import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Repository
public class DonneeUtilisateurImpl implements DonneeUtilisateur,UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(DonneeUtilisateurImpl.class);
    @Autowired
    private DBI dbiBean;

    public List<UtilisateurDTO> list(){
        Handle handle = dbiBean.open();
        UtilisateurSQLs UtilisateurSQLs = handle.attach(UtilisateurSQLs.class);
        return UtilisateurSQLs.list();
    }

    //implemente la sécurité
    @Autowired
    private DonneeRoleImpl implRole;

    @Override
    public UserDetails loadUserByUsername(String userName)throws UsernameNotFoundException {
        Objects.requireNonNull(userName);
        Handle handle = dbiBean.open();
        UtilisateurSQLs UtilisateurSQLs = handle.attach(UtilisateurSQLs.class);

        UtilisateurDTO user;
        try {
            user = UtilisateurSQLs.getByUsername(userName);
            if(user==null)throw new UsernameNotFoundException("User not found");
        }catch( SQLException e ){
            logger.error( e.getMessage()+"/sql : "+e.getSQLState() );
            throw new UsernameNotFoundException("User not found");
        };
        user.setAuthorities( implRole.getByCitoyenUserName( userName ) );
        logger.debug("role of the user" + user.getAuthorities() );
        return user;
    }

    @RegisterMapper(UtilisateurMapper.class)
    interface UtilisateurSQLs {
        @SqlQuery("select citoyen_id,login,password from citoyen")
        List<UtilisateurDTO> list();

        @SqlQuery("select citoyen_id,login,password from citoyen WHERE login=:login")
        UtilisateurDTO getByUsername(@Bind("login") String login) throws SQLException;
    }

    public static class UtilisateurMapper implements ResultSetMapper<UtilisateurDTO> {
        UtilisateurDTO usrDTO;
        public UtilisateurDTO map(final int i, final ResultSet r, final StatementContext statementContext) throws SQLException {
            usrDTO = new UtilisateurDTO();
            usrDTO.setId( (UUID) r.getObject("citoyen_id") );
            usrDTO.setUsername( r.getString("login") );
            usrDTO.setPassword( r.getString("password") );
            return usrDTO;
        }
    }
}


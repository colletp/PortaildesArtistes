package com.unamur.portaildesartistes.wsartiste.datalayer;

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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class DonneeUtilisateurImpl implements DonneeUtilisateur,UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(DonneeUtilisateurImpl.class);
    @Autowired
    private DBI dbiBean;

    @Autowired
    private DonneeCitoyenImpl citoyenImpl;

    public List<UtilisateurDTO> list(){
        Handle handle = dbiBean.open();
        UtilisateurSQLs UtilisateurSQLs = handle.attach(UtilisateurSQLs.class);
        return UtilisateurSQLs.list();
    }

    public UtilisateurDTO getById(UUID uuid){
        try{
            Handle handle = dbiBean.open();
            UtilisateurSQLs UtilisateurSQLs = handle.attach(UtilisateurSQLs.class);
            return UtilisateurSQLs.getById(uuid);
        }
        catch(UnableToExecuteStatementException e){
            System.err.println( e );
            System.err.println( e.getCause() );
            System.err.println( e.getMessage() );
            System.err.println( e.getClass() );
            //throw e;
        }
        catch(SQLException e){
            System.err.println( e );
            System.err.println( e.getCause() );
            System.err.println( e.getMessage() );
            System.err.println( e.getClass() );
            //throw e;
        }
        return null;
    }

    public UUID insert(UtilisateurDTO usr){
        return citoyenImpl.insert( usr );
    }

    public void update(UtilisateurDTO usr){
        try{
            Handle handle = dbiBean.open();
            UtilisateurSQLs UtilisateurSQLs = handle.attach(UtilisateurSQLs.class);
            UtilisateurSQLs.update(usr);
        }
            catch(UnableToExecuteStatementException e){
            System.err.println( e );
            System.err.println( e.getCause() );
            System.err.println( e.getMessage() );
            System.err.println( e.getClass() );
            //throw e;
        }
            catch(SQLException e){
            System.err.println( e );
            System.err.println( e.getCause() );
            System.err.println( e.getMessage() );
            System.err.println( e.getClass() );
            //throw e;
        }
    }

    public void delete(UUID uuid){
        try{
            Handle handle = dbiBean.open();
            UtilisateurSQLs UtilisateurSQLs = handle.attach(UtilisateurSQLs.class);
            UtilisateurSQLs.delete(uuid);
        }
        catch(UnableToExecuteStatementException e){
            System.err.println( e );
            System.err.println( e.getCause() );
            System.err.println( e.getMessage() );
            System.err.println( e.getClass() );
            //throw e;
        }
        catch(SQLException e){
            System.err.println( e );
            System.err.println( e.getCause() );
            System.err.println( e.getMessage() );
            System.err.println( e.getClass() );
            //throw e;
        }
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

        @SqlQuery("select citoyen_id,login,password from citoyen WHERE citoyen_id=:citoyen_id")
        UtilisateurDTO getById(@Bind("citoyen_id") UUID citoyen_id) throws SQLException;

        @SqlUpdate("UPDATE citoyen SET login=:username,password=:password WHERE citoyen_id=:id")
        void update(@BindBean UtilisateurDTO test) throws SQLException;

        @SqlUpdate("insert into citoyen (nom,prenom,date_naissance,tel,gsm,mail,nrn,nation,login,password,reside) values(:nom,:prenom,:dateNaissance,:tel,:gsm,:mail,:nrn,:nation,:username,:password,:reside) ")
        @GetGeneratedKeys
        UUID insert(@BindBean UtilisateurDTO test) throws SQLException;

        @SqlUpdate("DELETE FROM citoyen WHERE citoyen_id = :citoyen_id) ")
        void delete(@Bind("citoyen_id") UUID citoyen_id) throws SQLException;
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


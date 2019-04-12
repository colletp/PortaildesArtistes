package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
public class DonneeUtilisateurImpl implements DonneeUtilisateur,UserDetailsService{
    private static final Logger logger = LoggerFactory.getLogger(DonneeUtilisateurImpl.class);
    @Autowired
    private DBI dbiBean;

    public List<UtilisateurDTO> list(){
        Handle handle = dbiBean.open();
        UserSQLs userSQLs = handle.attach(UserSQLs.class);
        return userSQLs.list();
    }

    public UUID insert(UtilisateurDTO item){
        Handle handle = dbiBean.open();
        UserSQLs userSQLs = handle.attach(UserSQLs.class);
        UUID ret=null;
        try {
            ret = userSQLs.insert(item);
        }catch(UnableToExecuteStatementException e){
            System.err.println( e );
            System.err.println( e.getCause() );
            System.err.println( e.getMessage() );
            System.err.println( e.getClass() );
            throw e;
        }
        return ret;
    }
    public UserDetails loadUserByUsername(String p_login) {
        return getUserByLogin( p_login );
    }
    public UserDetails getUserByLogin(String p_login){
        Handle handle = dbiBean.open();
        UserSQLs userSQLs = handle.attach(UserSQLs.class);
        return userSQLs.getUserByLogin(p_login);
    }

    @RegisterMapper(UtilisateurMapper.class)
    interface UserSQLs {
        @SqlQuery("select * from citoyen")
        List<UtilisateurDTO> list();
        @SqlQuery("select * from citoyen WHERE login=:login")
        UserDetails getUserByLogin(@BindBean String login);
        @SqlUpdate("insert into citoyen (nom,prenom,date_naissance,tel,gsm,mail,nrn,nation,login,password,reside) values(:nom,:prenom,:dateNaissance,:tel,:gsm,:mail,:nrn,:nation,:login,:password,:reside) ")
        @GetGeneratedKeys
        UUID insert(@BindBean UtilisateurDTO test);
    }

    public static class UtilisateurMapper implements ResultSetMapper<UtilisateurDTO> {
        UtilisateurDTO usrDTO;
        public UtilisateurDTO map(final int i, final ResultSet r, final StatementContext statementContext) throws SQLException {
            usrDTO = new UtilisateurDTO();

            usrDTO.setId((UUID) r.getObject("citoyen_id"));
            usrDTO.setNom( r.getString("nom") );
            usrDTO.setPrenom( r.getString("prenom") );
            usrDTO.setDateNaissance( r.getDate("date_naissance") );
            usrDTO.setTel( r.getString("tel") );
            usrDTO.setGsm( r.getString("gsm") );
            usrDTO.setMail( r.getString("mail") );
            usrDTO.setNrn( r.getString("nrn") );
            usrDTO.setNation( r.getString("nation") );
            usrDTO.setUserName( r.getString("login") );
            usrDTO.setPassword( r.getString("password") );
            usrDTO.setReside( (UUID) r.getObject("reside"));

            return usrDTO;
        }
    }
}


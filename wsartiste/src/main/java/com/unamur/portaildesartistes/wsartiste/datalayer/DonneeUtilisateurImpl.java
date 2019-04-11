package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.wsartiste.corelayer.UtilisateurBean;
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
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
public class DonneeUtilisateurImpl implements DonneeUtilisateur,UserDetailsService{
    private static final Logger logger = LoggerFactory.getLogger(DonneeUtilisateurImpl.class);
    @Autowired
    private DBI dbiBean;
    @Autowired
    private DataSource dataSource;

    public List<UtilisateurBean> list(){
        Connection conn =  DataSourceUtils.getConnection(dataSource);
        Handle handle = dbiBean.open(conn);
        UserSQLs userSQLs = handle.attach(UserSQLs.class);
        return userSQLs.list();
    }

    public UUID insert(UtilisateurBean item){
        Connection conn =  DataSourceUtils.getConnection(dataSource);
        Handle handle = dbiBean.open(conn);
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
        Connection conn =  DataSourceUtils.getConnection(dataSource);
        Handle handle = dbiBean.open(conn);
        UserSQLs userSQLs = handle.attach(UserSQLs.class);
        return userSQLs.getUserByLogin(p_login);
    }

    @RegisterMapper(UtilisateurMapper.class)
    interface UserSQLs {
        @SqlQuery("select * from citoyen")
        List<UtilisateurBean> list();
        @SqlQuery("select * from citoyen WHERE login=:login")
        UserDetails getUserByLogin(@BindBean String login);
        @SqlUpdate("insert into citoyen (nom,prenom,date_naissance,tel,gsm,mail,nrn,nation,login,password,reside) values(:nom,:prenom,:dateNaissance,:tel,:gsm,:mail,:nrn,:nation,:login,:password,:reside) ")
        @GetGeneratedKeys
        UUID insert(@BindBean UtilisateurBean test);
    }

    public static class UtilisateurMapper implements ResultSetMapper<UtilisateurBean> {
        UtilisateurBean bean;
        public UtilisateurBean map(final int i, final ResultSet r, final StatementContext statementContext) throws SQLException {
            bean = new UtilisateurBean();

            bean.setId((UUID) r.getObject("citoyen_id"));
            bean.setNom( r.getString("nom") );
            bean.setPrenom( r.getString("prenom") );
            bean.setDateNaissance( r.getDate("date_naissance") );
            bean.setTel( r.getString("tel") );
            bean.setGsm( r.getString("gsm") );
            bean.setMail( r.getString("mail") );
            bean.setNrn( r.getString("nrn") );
            bean.setNation( r.getString("nation") );
            bean.setLogin( r.getString("login") );
            bean.setPassword( r.getString("password") );
            bean.setReside( (UUID) r.getObject("reside"));

            //bean.setResideAdr( adrImpl.getById( bean.getReside() ) );
            //bean.setRoles( roleImpl.getByUser( bean.getId() ) );
            return bean;
        }
    }
}


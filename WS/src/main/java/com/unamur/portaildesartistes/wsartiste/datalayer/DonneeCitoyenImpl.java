package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.CitoyenDTO;
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
import java.util.*;

@Repository
public class DonneeCitoyenImpl implements DonneeCitoyen,UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(DonneeCitoyenImpl.class);
    @Autowired
    private DBI dbiBean;

    public List<CitoyenDTO> list(){
        Handle handle = dbiBean.open();
        CitoyenSQLs CitoyenSQLs = handle.attach(CitoyenSQLs.class);
        return CitoyenSQLs.list();
    }
    public CitoyenDTO getById(UUID p_id){
        Handle handle = dbiBean.open();
        CitoyenSQLs CitoyenSQLs = handle.attach(CitoyenSQLs.class);
        try {
            return CitoyenSQLs.getById(p_id);
        }catch(SQLException e){
            return null;
        }
    }

    public UUID insert(CitoyenDTO item){
        Handle handle = dbiBean.open();
        CitoyenSQLs CitoyenSQLs = handle.attach(CitoyenSQLs.class);
        UUID ret=null;
        try {
            ret = CitoyenSQLs.insert(item);
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
        return ret;
    }

//implemente la sécurité
    @Autowired
    private DonneeRoleImpl implRole;

    @Override
    public UserDetails loadUserByUsername(String userName)throws UsernameNotFoundException {
        Objects.requireNonNull(userName);
        Handle handle = dbiBean.open();
        CitoyenSQLs CitoyenSQLs = handle.attach(CitoyenSQLs.class);

        CitoyenDTO user;
        try {
            user = CitoyenSQLs.getByLogin(userName);
            if(user==null)throw new UsernameNotFoundException("User not found");
        }catch( SQLException e ){
            logger.error( e.getMessage()+"/sql : "+e.getSQLState() );
            throw new UsernameNotFoundException("User not found");
        };
        user.setAuthorities( implRole.getByCitoyenId( user.getId() ) );
        logger.debug("role of the user" + user.getAuthorities() );
        return user;
    }

/*
    public Set<? extends GrantedAuthority> getAuthorities(){
        return authorities;
    }

    private void setAuthorities(//Set<? extends GrantedAuthority> authorities
                                ){
        this.authorities=implRole.getByCitoyenId( getById() );
    }
*/
    @RegisterMapper(CitoyenMapper.class)
    interface CitoyenSQLs {
        @SqlQuery("select * from citoyen")
        List<CitoyenDTO> list();

        @SqlQuery("select * from citoyen WHERE citoyen_id = :p_id ")
        CitoyenDTO getById(@Bind("p_id")UUID p_id) throws SQLException;

        @SqlQuery("select * from citoyen WHERE login=:login")
        CitoyenDTO getByLogin(@Bind("login") String login) throws SQLException;

        @SqlUpdate("insert into citoyen (nom,prenom,date_naissance,tel,gsm,mail,nrn,nation,login,password,reside) values(:nom,:prenom,:dateNaissance,:tel,:gsm,:mail,:nrn,:nation,:login,:password,:reside) ")
        @GetGeneratedKeys
        UUID insert(@BindBean CitoyenDTO test) throws SQLException;
    }

    public static class CitoyenMapper implements ResultSetMapper<CitoyenDTO> {
        CitoyenDTO usrDTO;
        public CitoyenDTO map(final int i, final ResultSet r, final StatementContext statementContext) throws SQLException {
            usrDTO = new CitoyenDTO();

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


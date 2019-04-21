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
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class DonneeCitoyenImpl implements DonneeCitoyen {
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

    @RegisterMapper(CitoyenMapper.class)
    interface CitoyenSQLs {
        @SqlQuery("select * from citoyen")
        List<CitoyenDTO> list();

        @SqlQuery("select * from citoyen WHERE citoyen_id = :p_id ")
        CitoyenDTO getById(@Bind("p_id")UUID p_id) throws SQLException;

        @SqlUpdate("insert into citoyen (nom,prenom,date_naissance,tel,gsm,mail,nrn,nation,login,password,reside) values(:nom,:prenom,:dateNaissance,:tel,:gsm,:mail,:nrn,:nation,:login,:password,:reside) ")
        @GetGeneratedKeys
        UUID insert(@BindBean CitoyenDTO test) throws SQLException;
    }

    public static class CitoyenMapper implements ResultSetMapper<CitoyenDTO> {
        CitoyenDTO citoyenDTO;
        public CitoyenDTO map(final int i, final ResultSet r, final StatementContext statementContext) throws SQLException {
            citoyenDTO = new CitoyenDTO();

            citoyenDTO.setId((UUID) r.getObject("citoyen_id"));
            citoyenDTO.setNom( r.getString("nom") );
            citoyenDTO.setPrenom( r.getString("prenom") );
            citoyenDTO.setDateNaissance( r.getDate("date_naissance") );
            citoyenDTO.setTel( r.getString("tel") );
            citoyenDTO.setGsm( r.getString("gsm") );
            citoyenDTO.setMail( r.getString("mail") );
            citoyenDTO.setNrn( r.getString("nrn") );
            citoyenDTO.setNation( r.getString("nation") );
            citoyenDTO.setReside( (UUID) r.getObject("reside"));

            return citoyenDTO;
        }
    }
}


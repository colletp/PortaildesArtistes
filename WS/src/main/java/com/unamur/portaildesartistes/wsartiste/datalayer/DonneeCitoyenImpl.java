package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.CitoyenDTO;
import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
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
import java.util.List;
import java.util.UUID;

@Repository
public class DonneeCitoyenImpl extends Donnee<CitoyenDTO> {
    private static final Logger logger = LoggerFactory.getLogger(DonneeCitoyenImpl.class);

    @Autowired
    DonneeAdresseImpl adrImpl;

    public List<CitoyenDTO> list(){
        return super.Exec(CitoyenSQLs.class).list();
    }
    public CitoyenDTO getById(UUID p_id){
        try {
            return super.Exec(CitoyenSQLs.class).getById(p_id);
        }catch(SQLException e){
            return null;
        }
    }

    @Override
    public UUID insert(CitoyenDTO item) {
        throw new RuntimeException("Not implemented. Use UtilisateurDTO param instead");
    }

    @Override
    public void update(CitoyenDTO item) {
        throw new RuntimeException("Not implemented. Use UtilisateurDTO param instead");
    }

    @Override
    public void delete(UUID id) {
        throw new RuntimeException("Not implemented. Use UtilisateurDTO param instead");
    }

    public UUID insert(UtilisateurDTO item){
        try {
            UUID reside =  adrImpl.insert( item.getCitoyen().getResideAdr() );
            item.getCitoyen().setReside( reside );
            return UUID.fromString( super.Exec(UtilisateurSQLs.class).insert( item , item.getCitoyen() ) );
        }
        catch(SQLException e){
            System.err.println( e.getCause().getMessage() );
            System.err.println( e.getMessage() );
            System.err.println( e.getClass() );
            return null;
        }
    }
    public void update(UtilisateurDTO item){
        adrImpl.update( item.getCitoyen().getResideAdr() );
        try{
            if( item.getPassword().isEmpty() )
                super.Exec(UtilisateurSQLs.class).update( item , item.getCitoyen() );
            else
                super.Exec(UtilisateurSQLs.class).updatePass( item , item.getCitoyen() );
        }catch(UnableToExecuteStatementException e){
            logger.error( e.getCause().getMessage() );
            logger.error( e.getMessage() );
            logger.error( e.getClass().getName() );
        }

    }

    @RegisterMapper(CitoyenMapper.class)
    interface CitoyenSQLs {
        @SqlQuery("select * from citoyen")
        List<CitoyenDTO> list();

        @SqlQuery("select * from citoyen WHERE citoyen_id = :p_id ")
        CitoyenDTO getById(@Bind("p_id")UUID p_id) throws SQLException;

    }
    @RegisterMapper(UtilisateurMapper.class)
    interface UtilisateurSQLs {
        @SqlQuery("INSERT INTO citoyen ( nom, prenom,date_naissance, tel, gsm, mail, nrn, nation, login   , password, reside) " +
                               "VALUES (:nom,:prenom,:dateNaissance,:tel,:gsm,:mail,:nrn,:nation,:username,:password,:reside) RETURNING citoyen_id ")
        String insert(@BindBean UtilisateurDTO usr,@BindBean CitoyenDTO cit) throws SQLException;

        @SqlUpdate("UPDATE citoyen SET nom=:nom, prenom=:prenom,date_naissance=:dateNaissance, tel=:tel, gsm=:gsm, mail=:mail, nrn=:nrn, nation=:nation WHERE citoyen_id=:id")
        void update(@BindBean UtilisateurDTO usr,@BindBean CitoyenDTO cit);

        @SqlUpdate("UPDATE citoyen SET nom=:nom, prenom=:prenom,date_naissance=:dateNaissance, tel=:tel, gsm=:gsm, mail=:mail, nrn=:nrn, nation=:nation, login=:username, password=:password WHERE citoyen_id=:id")
        void updatePass(@BindBean UtilisateurDTO usr,@BindBean CitoyenDTO cit);
    }

    public static class CitoyenMapper implements ResultSetMapper<CitoyenDTO> {
        public CitoyenDTO map(final int i, final ResultSet r, final StatementContext statementContext) throws SQLException {
            CitoyenDTO citoyenDTO = new CitoyenDTO();

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
    public static class UtilisateurMapper implements ResultSetMapper<UtilisateurDTO> {
        public UtilisateurDTO map(final int i, final ResultSet r, final StatementContext statementContext) throws SQLException {
            UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
            utilisateurDTO.setId((UUID) r.getObject("citoyen_id"));
            utilisateurDTO.setUsername( r.getString("login") );
            utilisateurDTO.setPassword( r.getString("password") );

            CitoyenDTO citoyenDTO = new CitoyenDTO();
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
            utilisateurDTO.setCitoyen(citoyenDTO);
            return utilisateurDTO;
        }
    }
}


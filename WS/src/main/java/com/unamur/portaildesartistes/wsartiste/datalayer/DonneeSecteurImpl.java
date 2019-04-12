package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.SecteurDTO;
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
public class DonneeSecteurImpl implements DonneeSecteur{
    private static final Logger logger = LoggerFactory.getLogger(DonneeCitoyenImpl.class);

    @Autowired
    private DBI dbiBean;

    public List<SecteurDTO> list(){
        Handle handle = dbiBean.open();
        SecteurSQLs SecteurSQLs = handle.attach(SecteurSQLs.class);
        return SecteurSQLs.list();
    }
    public SecteurDTO getById(UUID p_id){
        Handle handle = dbiBean.open();
        SecteurSQLs SecteurSQLs = handle.attach(SecteurSQLs.class);
        return SecteurSQLs.getById(p_id);
    }

    public UUID insert(SecteurDTO item){
        Handle handle = dbiBean.open();
        SecteurSQLs SecteurSQLs = handle.attach(SecteurSQLs.class);
        return SecteurSQLs.insert(item);
    }

    @RegisterMapper(SecteurMapper.class)
    interface SecteurSQLs {
        @SqlQuery("select * from secteur ")
        List<SecteurDTO> list();

        @SqlQuery("select * from secteur WHERE secteur_id = :p_id ")
        SecteurDTO getById(@Bind("p_id") UUID p_id);

        @SqlUpdate("INSERT INTO secteur (nom_secteur) VALUES (:nomSecteur) ")
        @GetGeneratedKeys
        UUID insert(@BindBean SecteurDTO test);
    }

    public static class SecteurMapper implements ResultSetMapper<SecteurDTO> {
        SecteurDTO sectDTO;
        public SecteurDTO map(final int i, final ResultSet r, final StatementContext statementContext) throws SQLException {
            sectDTO = new SecteurDTO();
            sectDTO.setId((UUID) r.getObject("secteur_id"));
            sectDTO.setNomSecteur(r.getString("nom_secteur"));
            return sectDTO;
        }
    }
}
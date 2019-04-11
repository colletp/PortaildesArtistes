package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.dtoArtiste.corelayer.AdresseDTO;

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
//@Configurable
public class DonneeAdresseImpl implements DonneeAdresse{
    private static final Logger logger = LoggerFactory.getLogger(DonneeAdresseImpl.class);

    @Autowired
    private DBI dbiBean;

    public List<AdresseDTO> list(){
        Handle handle = dbiBean.open();
        AdresseSQLs AdresseSQLs = handle.attach(AdresseSQLs.class);
        return AdresseSQLs.list();
    }

    public AdresseDTO getById(UUID p_id){
        Handle handle = dbiBean.open();
        AdresseSQLs AdresseSQLs = handle.attach(AdresseSQLs.class);
        return AdresseSQLs.getById( p_id );
    }

    public UUID insert(AdresseDTO item){
        Handle handle = dbiBean.open();
        AdresseSQLs AdresseSQLs = handle.attach(AdresseSQLs.class);
        return AdresseSQLs.insert(item);
    }

    @RegisterMapper(AdresseMapper.class)
    interface AdresseSQLs {
        @SqlQuery("select * from adresses")
        List<AdresseDTO> list();

        @SqlQuery("select * from adresses where adresses_id = :adresses_id")
        AdresseDTO getById(@Bind("adresses_id") UUID p_id);

        @SqlUpdate("insert into adresses (ville,rue,num,boite) values(:ville,:rue,:num,:boite) ")
        @GetGeneratedKeys
        UUID insert(@BindBean AdresseDTO test);
    }

    public static class AdresseMapper implements ResultSetMapper<AdresseDTO> {
        AdresseDTO bean;
        @Override
        public AdresseDTO map(final int i, final ResultSet r, final StatementContext statementContext) throws SQLException {
            bean = new AdresseDTO();
            bean.setId((UUID) r.getObject("adresses_id"));
            bean.setRue(r.getString("rue"));
            bean.setNumero(r.getString("num"));
            bean.setBoite(r.getString("boite"));
            bean.setVille(r.getString("ville"));
            return bean;
        }
    }

}


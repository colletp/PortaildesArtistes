package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.wsartiste.corelayer.AdresseBean;

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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
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

    @Autowired
    private DataSource dataSource;

    public List<AdresseBean> list(){
        Connection conn =  DataSourceUtils.getConnection(dataSource);
        Handle handle = dbiBean.open(conn);
        AdresseSQLs AdresseSQLs = handle.attach(AdresseSQLs.class);
        return AdresseSQLs.list();
    }

    public AdresseBean getById(UUID p_id){
        Connection conn =  DataSourceUtils.getConnection(dataSource);
        Handle handle = dbiBean.open(conn);
        AdresseSQLs AdresseSQLs = handle.attach(AdresseSQLs.class);
        return AdresseSQLs.getById( p_id );
    }

    public UUID insert(AdresseBean item){
        Connection conn =  DataSourceUtils.getConnection(dataSource);
        Handle handle = dbiBean.open(conn);
        AdresseSQLs AdresseSQLs = handle.attach(AdresseSQLs.class);
        return AdresseSQLs.insert(item);
    }

    @RegisterMapper(AdresseMapper.class)
    interface AdresseSQLs {
        @SqlQuery("select * from adresses")
        List<AdresseBean> list();

        @SqlQuery("select * from adresses where adresses_id = :adresses_id")
        AdresseBean getById(@Bind("adresses_id") UUID p_id);

        @SqlUpdate("insert into adresses (ville,rue,num,boite) values(:ville,:rue,:num,:boite) ")
        @GetGeneratedKeys
        UUID insert(@BindBean AdresseBean test);
    }

    @Component
    public static class AdresseMapper implements ResultSetMapper<AdresseBean> {
        AdresseBean bean;
        @Override
        public AdresseBean map(final int i, final ResultSet r, final StatementContext statementContext) throws SQLException {
            bean = new AdresseBean();
            bean.setId((UUID) r.getObject("adresses_id"));
            bean.setRue(r.getString("rue"));
            bean.setNumero(r.getString("num"));
            bean.setBoite(r.getString("boite"));
            bean.setVille(r.getString("ville"));
            return bean;
        }
    }

}


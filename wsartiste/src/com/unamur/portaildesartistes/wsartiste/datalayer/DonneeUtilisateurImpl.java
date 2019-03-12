package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.wsartiste.corelayer.AuthentificationBean;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class DonneeUtilisateurImpl {

        @Qualifier("dataSource")
//        @Autowired
        private DataSource dataSource;

        public List<AuthentificationBean> list(){
            // DatasourceUtils contrôle si il y a déjà une connecection avec une transaction pour le threa courant
            Connection conn =  DataSourceUtils.getConnection(dataSource);
            Handle handle = DBI.open(conn);
            UserSQLs userQLs = handle.attach(UserSQLs.class);
            return userQLs.list();
        }

        public Integer insert(AuthentificationBean item){
            Connection conn =  DataSourceUtils.getConnection(dataSource);
            Handle handle = DBI.open(conn);
            UserSQLs userSQLs = handle.attach(UserSQLs.class);
            return userSQLs.insert(item);
        }

        @RegisterMapper(UtilisateurMapper.class)
        interface UserSQLs {
            @SqlQuery("select * from TP_UTILISATEUR")
            List<AuthentificationBean> list();

            @SqlUpdate("insert into TP_UTILISATEUR (TPU_NAME)" +
                " values(:username) ")
            @GetGeneratedKeys
            Integer insert(@BindBean AuthentificationBean test);
        }

        public class UtilisateurMapper
            implements ResultSetMapper<AuthentificationBean> {

            @Override
            public AuthentificationBean map(final int i, final ResultSet r, final StatementContext statementContext) throws SQLException {
                AuthentificationBean bean = new AuthentificationBean();
                bean.setId((Integer) r.getObject("TPU_ID"));
                bean.setNomUtilisateur(r.getString("TPU_NOM"));
                return bean;
            }
        }

    }


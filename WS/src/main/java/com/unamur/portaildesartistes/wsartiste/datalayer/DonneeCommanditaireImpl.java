package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.CommanditaireDTO;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
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
public class DonneeCommanditaireImpl implements DonneeCommanditaire{
    private static final Logger logger = LoggerFactory.getLogger(DonneeCommanditaireImpl.class);

    @Autowired
    private DBI dbiBean;

    public List<CommanditaireDTO> list(){
        Handle handle = dbiBean.open();
        CommanditaireSQLs CommanditaireSQLs = handle.attach(CommanditaireSQLs.class);
        return CommanditaireSQLs.list();
    }

    public UUID insert(CommanditaireDTO item){
        Handle handle = dbiBean.open();
        CommanditaireSQLs CommanditaireSQLs = handle.attach(CommanditaireSQLs.class);
        return CommanditaireSQLs.insert(item);
    }

    @RegisterMapper(CommanditaireMapper.class)
    interface CommanditaireSQLs {
        @SqlQuery("select * from commanditaire ")
        List<CommanditaireDTO> list();

        @SqlUpdate("INSERT INTO commanditaire (entreprise_id,citoyen_id) VALUES (:entreprise_id,:citoyen_id) ")
        @GetGeneratedKeys
        UUID insert(@BindBean CommanditaireDTO test);
    }

    public static class CommanditaireMapper implements ResultSetMapper<CommanditaireDTO> {
        CommanditaireDTO comDTO;
        public CommanditaireDTO map(final int i, final ResultSet r, final StatementContext statementContext) throws SQLException {
            comDTO = new CommanditaireDTO();
            comDTO.setId((UUID) r.getObject("com_id"));
            comDTO.setEntrepriseId((UUID) r.getObject("entreprise_id"));
            comDTO.setCitoyenId((UUID) r.getObject("citoyen_id"));
            return comDTO;
        }
    }
}
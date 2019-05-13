package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.DTO;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.util.StringColumnMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Array;
import java.sql.SQLException;
import java.util.*;

public abstract class Donnee<T extends DTO> {
    private static final Logger logger = LoggerFactory.getLogger(Donnee.class);

    @Autowired
    private DBI dbiBean;

    protected <SQLs> SQLs Exec( java.lang.Class<SQLs> clazz){
        Handle handle = dbiBean.open();
        SQLs ret = handle.attach( clazz );
        return ret;
    }
    protected void Exec( String query,UUID id,Object[] elems){
        Handle handle = dbiBean.open();
        try{
            Array a = handle.getConnection().createArrayOf("varchar",elems);
            handle.execute(query,a,id);
            handle.commit();
        }catch(SQLException e){
            logger.error(e.getMessage());
        }
    }

    public abstract List<T> list();
    public abstract T getById(UUID id);
    public abstract UUID insert(T item);
    public abstract void update(T item);
    public abstract void delete(UUID id);
}

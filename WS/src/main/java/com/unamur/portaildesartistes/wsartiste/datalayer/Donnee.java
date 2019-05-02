package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.DTO;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

public abstract class Donnee<T extends DTO> {
    @Autowired
    private DBI dbiBean;

    protected <SQLs> SQLs Exec( java.lang.Class<SQLs> clazz){
        Handle handle = dbiBean.open();
        SQLs ret = handle.attach( clazz );
        return ret;
    }

    abstract List<T> list();
    abstract T getById(UUID id);
    abstract UUID insert(T item);
    abstract void update(T item);
    abstract void delete(UUID id);
}
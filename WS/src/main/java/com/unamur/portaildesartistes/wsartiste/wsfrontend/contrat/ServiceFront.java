package com.unamur.portaildesartistes.wsartiste.wsfrontend.contrat;

import com.unamur.portaildesartistes.DTO.DTO;
import com.unamur.portaildesartistes.wsartiste.Business.IService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

public abstract class ServiceFront<T extends DTO> {
    @Autowired
    protected IService<T> srvImpl;

    protected UUID create( T objDTO )throws Exception{ return srvImpl.insert(objDTO); }
    protected T read( UUID id ){ return srvImpl.getById(id); }
    protected void update( T objDTO )throws Exception{ srvImpl.update(objDTO); }
    protected void delete( UUID id )throws Exception{ srvImpl.delete(id); }

    protected List<T> list(){ return srvImpl.list(); }

}

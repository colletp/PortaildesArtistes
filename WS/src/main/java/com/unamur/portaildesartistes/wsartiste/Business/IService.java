package com.unamur.portaildesartistes.wsartiste.Business;

import com.unamur.portaildesartistes.DTO.DTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public interface IService<T extends DTO> {
    List<T> list();
    T getById(UUID uuid);
    UUID insert(T item)throws Exception;
    void update(T item)throws Exception;
    void delete(UUID uuid)throws Exception;
}

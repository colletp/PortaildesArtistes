package com.unamur.portaildesartistes.wsartiste.Service;

import com.unamur.portaildesartistes.DTO.DTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public interface IService<T extends DTO> {
    List<T> list();
    T getById(UUID uuid);
    UUID insert(T item);
    void update(T item);
    void delete(UUID uuid);
}

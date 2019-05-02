package com.unamur.portaildesartistes.wsartiste.Service;

import java.util.List;
import java.util.UUID;

public interface IService<T> {
    List<T> list();
    T getById(UUID uuid);
    UUID insert(T item);
    void update(T item);
}

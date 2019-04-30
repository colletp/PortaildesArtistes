package com.unamur.portaildesartistes.wsartiste.gestionutilisateur;

import com.unamur.portaildesartistes.DTO.CitoyenDTO;
import com.unamur.portaildesartistes.DTO.UtilisateurDTO;

import java.util.List;
import java.util.UUID;

public interface Service<T> {
    List<T> list();
    T getById(UUID uuid);
    UUID insert(T item);
    void update(T item);
}

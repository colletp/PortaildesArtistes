package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.wsartiste.corelayer.AdresseBean;

import java.util.List;
import java.util.UUID;

public interface DonneeAdresse {
    List<AdresseBean> list();
    UUID insert(AdresseBean item);
}

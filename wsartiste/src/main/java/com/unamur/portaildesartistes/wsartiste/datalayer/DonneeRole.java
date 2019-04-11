package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.wsartiste.corelayer.RoleBean;

import java.util.List;
import java.util.UUID;

public  interface DonneeRole {
    List<RoleBean> getByUser(UUID p_id);
}

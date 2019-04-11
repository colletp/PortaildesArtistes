package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.dtoArtiste.corelayer.RoleDTO;

import java.util.List;
import java.util.UUID;

public  interface DonneeRole {
    List<RoleDTO> getByUser(UUID p_id);
}

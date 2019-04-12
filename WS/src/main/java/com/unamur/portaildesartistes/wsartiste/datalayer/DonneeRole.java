package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.RoleDTO;

import java.util.List;
import java.util.UUID;

public  interface DonneeRole {
    List<RoleDTO> getByCitoyenId(UUID p_id);
}

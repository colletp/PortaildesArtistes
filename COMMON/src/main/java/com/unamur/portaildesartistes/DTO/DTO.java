package com.unamur.portaildesartistes.DTO;

import java.io.Serializable;
import java.util.UUID;

public interface DTO extends Serializable {
    UUID getId();
    void setId( UUID p_id );
}

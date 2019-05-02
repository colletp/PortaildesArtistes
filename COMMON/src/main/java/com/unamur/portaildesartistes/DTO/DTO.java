package com.unamur.portaildesartistes.DTO;

import java.io.Serializable;
import java.util.UUID;

public abstract class DTO implements Serializable {
    private UUID id;
    
	public UUID getId() { return id; }
    public void setId( UUID p_id) { this.id = p_id; }
}

package com.bbva.tinfoilhat.dtos;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class TaskDone implements Serializable{

	@JsonIgnore
    private static final long serialVersionUID = 1L;
    
    private String status;

    private String name;

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
}
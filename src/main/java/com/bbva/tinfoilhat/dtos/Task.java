package com.bbva.tinfoilhat.dtos;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Task implements Serializable{

    @JsonIgnore
	private static final long serialVersionUID = 1L;

    //Clave de la tarea
    private String key;
    //Nombre de la tarea
    private String name;
    //Descripcion de la tarea
    private String description;
    //Estado de la tarea
    private String status;
    //Puntos que vale la tarea
    private Integer taskPoint;

    
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getTaskPoint() {
		return taskPoint;
	}

	public void setTaskPoint(Integer taskPoint) {
		this.taskPoint = taskPoint;
	}

}



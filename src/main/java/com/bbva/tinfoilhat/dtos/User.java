package com.bbva.tinfoilhat.dtos;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class User implements Serializable{

	@JsonIgnore
    private static final long serialVersionUID = 1L;
    
    private String id;

    private String name;

    private String surname;

    private Integer age;

    private String chatBotID;

    private Integer totalPoint;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getChatBotID() {
		return chatBotID;
	}

	public void setChatBotID(String chatBotID) {
		this.chatBotID = chatBotID;
	}

	public Integer getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(Integer totalPoint) {
		this.totalPoint = totalPoint;
	}



}
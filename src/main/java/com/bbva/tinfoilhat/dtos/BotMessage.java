package com.bbva.tinfoilhat.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class BotMessage implements Serializable {

	@JsonIgnore
    private static final long serialVersionUID = 1L;
    
    private String text;

    private Long conversationId;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Long getConversationId() {
		return conversationId;
	}

	public void setConversationId(Long conversationId) {
		this.conversationId = conversationId;
	}

}
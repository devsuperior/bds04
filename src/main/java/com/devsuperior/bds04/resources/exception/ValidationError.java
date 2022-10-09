package com.devsuperior.bds04.resources.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandartError implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> teste = new ArrayList<>();

	public List<FieldMessage> getErrors() {
		return teste;
	}
	
	public void addError(String field, String message) {
		this.getErrors().add(new FieldMessage(field, message));
	}
		
}

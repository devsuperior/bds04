package com.devsuperior.bds04.services.exceptions;

public class DataBaseException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public DataBaseException(String erro) {
		super(erro);
	}

}

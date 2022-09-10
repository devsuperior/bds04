package com.devsuperior.bds04.dto;

import javax.validation.constraints.NotBlank;

public class UserInsertDTO extends UserDTO {
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Campo requerido")
	private String password;

	UserInsertDTO() {
		super();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

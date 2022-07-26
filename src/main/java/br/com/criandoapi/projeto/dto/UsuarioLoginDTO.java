package br.com.criandoapi.projeto.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioLoginDTO {
	
	private String nome;
	private String email;
	private String senha;
	
	public UsuarioLoginDTO(String nome, String email, String senha) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
	}
}



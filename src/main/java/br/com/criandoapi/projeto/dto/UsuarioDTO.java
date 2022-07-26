package br.com.criandoapi.projeto.dto;

import br.com.criandoapi.projeto.model.Usuario;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
	
	private Integer id;	
	private String nome;	
	private String email;	
	private String senha;	
	private String telefone;
	
	
	
	public UsuarioDTO(Usuario usuario) {
		this.id = usuario.getId();
		this.nome = usuario.getNome();
		this.email = usuario.getEmail();
		this.senha = usuario.getSenha();
		this.telefone = usuario.getTelefone();
	}

}

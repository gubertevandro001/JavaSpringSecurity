package br.com.criandoapi.projeto.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.catalina.User;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.criandoapi.projeto.dto.UsuarioDTO;
import br.com.criandoapi.projeto.dto.UsuarioLoginDTO;
import br.com.criandoapi.projeto.model.Usuario;
import br.com.criandoapi.projeto.repository.IUsuario;
import br.com.criandoapi.projeto.security.Token;
import br.com.criandoapi.projeto.security.TokenUtil;

@Service
public class UsuarioService {
	
	private IUsuario repository;
		
	private PasswordEncoder passwordEncoder;
	
	public UsuarioService(IUsuario repository) {
		this.repository = repository;
		this.passwordEncoder = new BCryptPasswordEncoder();
	}
	
	public List<UsuarioDTO> listarUsuario() {
		List<Usuario> usuarios = repository.findAll();
		List<UsuarioDTO> usuariosDAO = usuarios.stream().map(user -> new UsuarioDTO(user)).collect(Collectors.toList());
		return usuariosDAO;
	}
	
	public UsuarioDTO criarUsuario(UsuarioDTO usuarioDTO) {
		Usuario usuario = new Usuario();
		String encoder = this.passwordEncoder.encode(usuarioDTO.getSenha());
		usuario.setSenha(encoder);
		usuario.setEmail(usuarioDTO.getEmail());
		usuario.setNome(usuarioDTO.getNome());
		usuario.setTelefone(usuarioDTO.getTelefone());
		UsuarioDTO usuarioResponseDTO = new UsuarioDTO(usuario);
		repository.save(usuario);
		return usuarioResponseDTO;
	}
	
	public UsuarioDTO editarUsuario(UsuarioDTO usuarioDTO) {
		Usuario usuario = new Usuario();
		String encoder = this.passwordEncoder.encode(usuarioDTO.getSenha());
		usuario.setSenha(encoder);
		usuario.setEmail(usuarioDTO.getEmail());
		usuario.setNome(usuarioDTO.getNome());
		usuario.setTelefone(usuarioDTO.getTelefone());
		UsuarioDTO usuarioResponseDTO = new UsuarioDTO(usuario);
		repository.save(usuario);
		return usuarioResponseDTO;
	}
	
	public Boolean deletarUsuario(Integer id) {
		repository.deleteById(id);
		return true;
	}

	public Boolean validarSenha(Usuario usuario) {
		String senha = repository.getById(usuario.getId()).getSenha();
		Boolean valid = passwordEncoder.matches(usuario.getSenha(), senha);
		return valid;
	}

	public Token gerarToken(@Valid UsuarioLoginDTO usuario) {
		Usuario user = repository.findBynomeOrEmail(usuario.getNome(), usuario.getEmail());
		if (user != null) {
			Boolean valid = passwordEncoder.matches(usuario.getSenha(), user.getSenha());
			Boolean vEmail = user.getEmail().equals(usuario.getEmail());
			Boolean vNome = user.getNome().equals(usuario.getNome());
			if(valid && vEmail && vNome) {
				return new Token(TokenUtil.createToken(user));
			}
		}
		return null;
	}
	
}

package br.com.criandoapi.projeto.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.criandoapi.projeto.dto.UsuarioDTO;
import br.com.criandoapi.projeto.dto.UsuarioLoginDTO;
import br.com.criandoapi.projeto.model.Usuario;
import br.com.criandoapi.projeto.security.Token;
import br.com.criandoapi.projeto.service.UsuarioService;

@RestController
@CrossOrigin("*")
@RequestMapping("/usuarios")
public class UsuarioController {

	private UsuarioService usuarioService;
	
	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	@GetMapping
	public ResponseEntity<List<UsuarioDTO>> listaUsuarios() {
		return ResponseEntity.ok().body(usuarioService.listarUsuario());
	}
	
	@PostMapping
	public ResponseEntity<UsuarioDTO> criarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
		return ResponseEntity.ok().body(usuarioService.criarUsuario(usuarioDTO));
	}
	
	@PutMapping
	public ResponseEntity<UsuarioDTO> editarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
		return ResponseEntity.ok().body(usuarioService.editarUsuario(usuarioDTO));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluirUsuario(@PathVariable Integer id) {
		usuarioService.deletarUsuario(id);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/login")
	public ResponseEntity<Token> logar(@Valid @RequestBody UsuarioLoginDTO usuario) {
		Token token = usuarioService.gerarToken(usuario);
		if (token != null) {
			return ResponseEntity.ok(token);
		}
		return ResponseEntity.status(403).build();
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}
}

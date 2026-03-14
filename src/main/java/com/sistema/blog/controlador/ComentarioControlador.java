package com.sistema.blog.controlador;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.blog.dto.ComentarioDTO;
import com.sistema.blog.servicio.ComentarioServicio;

@RestController
@RequestMapping("/api/")
public class ComentarioControlador {
	
	@Autowired
	private ComentarioServicio comentarioServicio;
	@GetMapping("/publicaciones/{publicacionId}/comentarios")
	public List<ComentarioDTO> listarComentarioPorPublicacionId(@PathVariable(value = "publicacionId") long publicacionId){
		return comentarioServicio.obtenerComentariosPorPublicacionId(publicacionId);	
	}
	
	@GetMapping("publicaciones/{publicacionId}/comentarios/{id}")
	public ResponseEntity<ComentarioDTO> obtenerComentarioPorId(@PathVariable(value = "publicacionId")long publicacionId,@PathVariable(value = "id") long comentarioId){
		return new ResponseEntity<> (comentarioServicio.obtenerComentarioPorId(publicacionId, comentarioId),HttpStatus.OK);
	}
	
	@PostMapping("/publicaciones/{publicacionId}/comentarios")
	public ResponseEntity<ComentarioDTO> guardarComentario(@PathVariable(value="publicacionId")long publicacionId,@Valid @RequestBody ComentarioDTO comentarioDTO){
		return new ResponseEntity<>(comentarioServicio.crearComentario(publicacionId, comentarioDTO),HttpStatus.CREATED);		
	}
	
	@PutMapping("publicaciones/{publicacionId}/comentarios")
	public ResponseEntity<ComentarioDTO> actualizarComentario(@PathVariable(value="publicacionId")long publicacionId,@Valid @RequestBody ComentarioDTO comentarioDTO){
		return new ResponseEntity<>(comentarioServicio.actualizarComentario(publicacionId, comentarioDTO),HttpStatus.OK);
	}
	
	@DeleteMapping("publicaciones/{publicacionId}/comentarios/{id}") 
	public ResponseEntity<String> eliminarComentario(@PathVariable(value = "publicacionId")long publicacionId,@PathVariable(value = "id") long comentarioId){
		comentarioServicio.eliminarComentario(publicacionId, comentarioId);
		return new ResponseEntity<>("Comentario eliminado con exito.",HttpStatus.OK);
	}

	
	
	

}

package com.sistema.blog.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import com.sistema.blog.dto.PublicacionDTO;
import com.sistema.blog.dto.PublicacionRespuesta;
import com.sistema.blog.servicio.PublicacionServicio;
import com.sistema.blog.utilerias.AppConstantes;

@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionControlador {
	
	@Autowired 
	private PublicacionServicio publicacionServicio;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<PublicacionDTO> guardarPublicacion(@Valid @RequestBody PublicacionDTO publicacionDTO){
		return new ResponseEntity<> (publicacionServicio.crearPublicacion(publicacionDTO),HttpStatus.CREATED);
	}

	@GetMapping 
	public PublicacionRespuesta listarPublicaciones(@RequestParam(value="pageNo",defaultValue=AppConstantes.NUMERO_PAGINA,required=false) int numeroPagina,
			@RequestParam(value="pageSize",defaultValue=AppConstantes.MEDIDA_PAGINA,required=false) int medidaPagina,
			@RequestParam(value="sortBy",defaultValue=AppConstantes.ORDENAR,required=false)String ordernarPor,
			@RequestParam(value="sortDir",defaultValue=AppConstantes.ORDERNAR_DIRECCION,required=false)String ordenDir){
		return publicacionServicio.obtenerAllPublicaciones(numeroPagina,medidaPagina,ordernarPor,ordenDir);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PublicacionDTO> obtenerPublicacionPorId(@PathVariable(name="id") Long id){
		return ResponseEntity.ok(publicacionServicio.obtenerPublicacionesPorId(id));		
	}
	
	@PutMapping
	public ResponseEntity<PublicacionDTO> actualizarPublicacion(@Valid @RequestBody PublicacionDTO publicacionDTO) {
		return new ResponseEntity<>(publicacionServicio.actualizarPublicacion(publicacionDTO),HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping({"/{id}"})
	public ResponseEntity<String> eliminarPublicacion(@PathVariable(name="id") long id){
		publicacionServicio.eliminarPublicacion(id);
		return new ResponseEntity<>("Publicacion eliminada con exito",HttpStatus.OK);
	}
	
	
	

}

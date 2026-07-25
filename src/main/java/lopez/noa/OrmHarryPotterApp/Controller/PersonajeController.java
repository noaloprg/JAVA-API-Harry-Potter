package lopez.noa.OrmHarryPotterApp.Controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lopez.noa.OrmHarryPotterApp.DTO.PersonajeDTO.*;
import lopez.noa.OrmHarryPotterApp.Servicios.PersonajeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/personajes")
public class PersonajeController {
    private final PersonajeService servicio;

    public PersonajeController(PersonajeService servicio) {
        this.servicio = servicio;
    }

    //Metodos de OBTENCION
    @GetMapping
    public ResponseEntity<List<PersonajeResponseDTO>> getAll() {
        return ResponseEntity.ok(servicio.getAll());
    }

    @GetMapping("/nombre/{palabra}")
    public ResponseEntity<List<PersonajeResponseDTO>> getPersonajeByNameContaning(@PathVariable("palabra") String palabra) {
        return ResponseEntity.ok(servicio.getPersonajeContaining(palabra));
    }

    @GetMapping("/casa/{nombreCasa}")
    public ResponseEntity<List<PersonajeResponseDTO>> getPersonajeByCasa(@PathVariable("nombreCasa") String nombre) {
        return ResponseEntity.ok(servicio.getPersonajesByCasa(nombre));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonajeResponseDTO> getById(@PathVariable("id") int id) {
        return ResponseEntity.ok(servicio.getById(id));
    }

    //CREACION
    //crear personaje normal
    @PostMapping
    public ResponseEntity<PersonajeResponseDTO> crearPersonaje(@Valid @RequestBody PersonajeCreateDTO personaje) {
        return ResponseEntity.ok(servicio.create(personaje));
    }

    //crear personaje con varita
    @PostMapping("/crear-con-varita")
    @Transactional
    public ResponseEntity<PersonajeVaritaResponseDTO> createPersonajeVarita(@Valid @RequestBody PersonajeVaritaCreateDTO dto) {
        return ResponseEntity.ok(servicio.createConVarita(dto));
    }

    //PUT
    //asignar un personaje a una varita
    @PutMapping("/{idPersonaje}/varita/{idVarita}")
    public ResponseEntity<PersonajeVaritaAsignadaResponseDTO> asignarPersonajeVarita(@PathVariable("idPersonaje") Integer idPer, @PathVariable("idVarita") Integer idVar) {
        return ResponseEntity.ok(servicio.addVarita(idPer, idVar));
    }
}

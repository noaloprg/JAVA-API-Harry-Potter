package lopez.noa.OrmHarryPotterApp.Controller;

import jakarta.validation.Valid;
import lopez.noa.OrmHarryPotterApp.DTO.HechizoDTO.HechizoCreateDTO;
import lopez.noa.OrmHarryPotterApp.DTO.HechizoDTO.HechizoResponseDTO;
import lopez.noa.OrmHarryPotterApp.Servicios.HechizoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/hechizos")
public class HechizoController {

    private final HechizoService servicio;

    public HechizoController(HechizoService servicio) {
        this.servicio = servicio;
    }

    //GET
    @GetMapping
    public ResponseEntity<List<HechizoResponseDTO>> getAll() {
        return ResponseEntity.ok(servicio.getAll());
    }

    //con paginacion
    @GetMapping("/paginados")
    public ResponseEntity<Page<HechizoResponseDTO>> getByPageable(@PageableDefault(size = 5) Pageable paginacion) {
        return ResponseEntity.ok(servicio.getSegunPaginado(paginacion));
    }

    @GetMapping("/paginados2")
    public ResponseEntity<Page<HechizoResponseDTO>> getByPageableSort(@PageableDefault Pageable paginacion, @RequestParam(required = false) String ordenacion) {
        return ResponseEntity.ok(servicio.getSegunPaginadoOrdenado(paginacion, ordenacion));

    }

    //CREACION
    //insert masivo
    @PostMapping("/crear-masivo")
    public ResponseEntity<List<HechizoResponseDTO>> createVariosHechizos(@Valid @RequestBody List<HechizoCreateDTO> listaDto) {
        return ResponseEntity.ok(servicio.createMuchosHechizos(listaDto));
    }
}

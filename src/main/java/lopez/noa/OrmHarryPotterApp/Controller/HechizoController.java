package lopez.noa.OrmHarryPotterApp.Controller;

import jakarta.validation.Valid;
import lopez.noa.OrmHarryPotterApp.DTO.HechizoDTO.HechizoCreateDTO;
import lopez.noa.OrmHarryPotterApp.DTO.HechizoDTO.HechizoResponseDTO;
import lopez.noa.OrmHarryPotterApp.Servicios.HechizoService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/hechizos")
public class HechizoController {

    private final HechizoService servicio;

    public HechizoController(HechizoService servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public ResponseEntity<List<HechizoResponseDTO>> getAll() {
        return ResponseEntity.ok(servicio.getAll());
    }

    //insert masivo
    @PostMapping("/crear-masivo")
    public ResponseEntity<List<HechizoResponseDTO>> createVariosHechizos(@Valid @RequestBody List<HechizoCreateDTO> listaDto) {
        return ResponseEntity.ok(servicio.createMuchosHechizos(listaDto));
    }
}

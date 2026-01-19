package lopez.noa.OrmHarryPotterApp.Controller;

import jakarta.validation.Valid;
import lopez.noa.OrmHarryPotterApp.DTO.VaritaDTO.VaritaCreateDTO;
import lopez.noa.OrmHarryPotterApp.DTO.VaritaDTO.VaritaResponseDTO;
import lopez.noa.OrmHarryPotterApp.DTO.VaritaDTO.VaritaSummaryDTO;
import lopez.noa.OrmHarryPotterApp.Servicios.VaritaService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/varitas")
public class VaritaController {

    private final VaritaService servicio;

    //constructor
    public VaritaController(VaritaService servicio) {
        this.servicio = servicio;
    }

    //GET
    @GetMapping
    public ResponseEntity<List<VaritaResponseDTO>> getVaritas() {
        return ResponseEntity.ok(servicio.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VaritaResponseDTO> getVaritaById(@PathVariable int id) {
        return ResponseEntity.ok(servicio.getById(id));
    }


    @GetMapping("/estado")
    //si no indica nada, por defecto seran todas
    public ResponseEntity<List<VaritaResponseDTO>> getVaritasRotas(@RequestParam boolean rota) {
        //si es true devuelve las rotas, sino todas
        return rota ? ResponseEntity.ok(servicio.getAll())
                : ResponseEntity.ok(servicio.getAllRotas(rota));
    }

    @GetMapping("/nucleo")
    public ResponseEntity<List<VaritaResponseDTO>> getVaritasNucleo(@RequestParam String nucleo) {
        return ResponseEntity.ok(servicio.getByNucleo(nucleo));
    }

    @GetMapping("/resumen")
    public ResponseEntity<List<VaritaSummaryDTO>> getVaritasResumen() {
        return ResponseEntity.ok(servicio.getResumen());
    }

    //ACTUALIZACION
    @PutMapping("/varita/romper/{id}")
    public ResponseEntity<VaritaResponseDTO> setRomperVarita(@PathVariable("id") int id) {
        return ResponseEntity.ok(servicio.setRota(id));
    }
    @PutMapping("/varita/romper/android/{id}")
    public ResponseEntity<Boolean> setRomperVaritaAndroid(@PathVariable("id") int id) {
        return ResponseEntity.ok(servicio.setRota(id) != null);
    }


    @PutMapping("/{id}")
    public ResponseEntity<VaritaSummaryDTO> actualizarVarita(@PathVariable("id") int id, @Valid @RequestBody VaritaCreateDTO var) {
        return ResponseEntity.ok(servicio.updateVarita(id, var));
    }

    //CREACION
    @PostMapping
    public ResponseEntity<VaritaSummaryDTO> createVarita(@Valid @RequestBody VaritaCreateDTO varita) {
        return ResponseEntity.ok(servicio.createVarita(varita));
    }

    @PostMapping("/crear")
    public ResponseEntity<Boolean> createVaritaAndroidStudio(@Valid @RequestBody VaritaCreateDTO varita) {
        return ResponseEntity.ok(servicio.createVarita(varita) != null );
    }
}

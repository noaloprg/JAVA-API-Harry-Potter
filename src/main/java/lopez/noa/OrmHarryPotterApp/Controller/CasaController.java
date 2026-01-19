package lopez.noa.OrmHarryPotterApp.Controller;

import jakarta.validation.Valid;
import lopez.noa.OrmHarryPotterApp.DTO.CasaDTO.CasaCreateDTO;
import lopez.noa.OrmHarryPotterApp.DTO.CasaDTO.CasaResponseDTO;
import lopez.noa.OrmHarryPotterApp.Mappers.CasaMapper;
import lopez.noa.OrmHarryPotterApp.Servicios.CasaService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/casas")
public class CasaController {

    private final CasaService servicio;

    public CasaController(CasaService servicio){
        this.servicio = servicio;
    }

    @GetMapping
    public ResponseEntity<List<CasaResponseDTO>> getAll(){
        return ResponseEntity.ok(servicio.getAll());
    }

    @PostMapping
    public ResponseEntity<CasaResponseDTO> create(@Valid @RequestBody CasaCreateDTO casa){
        return ResponseEntity.ok(CasaMapper.toCasaResponse(servicio.createCasa(casa)));
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<CasaResponseDTO> delete(@PathVariable("id") Integer id){
        return ResponseEntity.ok(servicio.deleteById(id));
    }

}

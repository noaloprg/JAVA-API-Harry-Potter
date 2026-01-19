package lopez.noa.OrmHarryPotterApp.DTO.PersonajeDTO;

import lopez.noa.OrmHarryPotterApp.DTO.VaritaDTO.VaritaResponseDTO;

import java.util.List;

public record PersonajeVaritaResponseDTO (
        PersonajeResponseDTO personaje,
        List<VaritaResponseDTO> listaVaritas
){
}

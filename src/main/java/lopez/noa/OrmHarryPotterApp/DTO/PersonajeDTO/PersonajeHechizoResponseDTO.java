package lopez.noa.OrmHarryPotterApp.DTO.PersonajeDTO;

import lopez.noa.OrmHarryPotterApp.DTO.HechizoDTO.HechizoResponseDTO;

import java.util.List;

public record PersonajeHechizoResponseDTO(
        PersonajeResponseDTO personaje,
        List<HechizoResponseDTO> hechizo
){
}

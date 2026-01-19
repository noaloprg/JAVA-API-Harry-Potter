package lopez.noa.OrmHarryPotterApp.DTO.PersonajeDTO;

import lopez.noa.OrmHarryPotterApp.DTO.VaritaDTO.VaritaResponseDTO;

public record PersonajeVaritaAsignadaResponseDTO(
        int id,
        String nombrePersonaje,
        VaritaResponseDTO varita
){

}

package lopez.noa.OrmHarryPotterApp.DTO.PersonajeDTO;

import lopez.noa.OrmHarryPotterApp.Modelos.TipoSangre;

public record PersonajeResponseDTO(
        Integer id,
        String nombre,
        TipoSangre sangre,
        Integer idCasa
) {
}

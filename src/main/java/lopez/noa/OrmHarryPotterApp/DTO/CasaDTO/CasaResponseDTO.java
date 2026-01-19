package lopez.noa.OrmHarryPotterApp.DTO.CasaDTO;

public record CasaResponseDTO(
        Integer id,
        String escudoImagen,
        String fundador,
        String nombre
        //no mostrar todos los alumnos que pertenecen por si son muchos
) {
}

package lopez.noa.OrmHarryPotterApp.DTO.CasaDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CasaCreateDTO(

        @Size(max = 50, message = "El máximo de caracteres del 'escudoImagen' de Casa es de 50")
        String escudoImagen,

        @NotBlank(message = "Debe asignarse un valor al atributo")
        @Size(max = 85, message = "El máximo de caracteres del 'fundador' de Casa es de 85")
        String fundador,

        @NotBlank(message = "Debe asignarse un valor al atributo")
        @Size(max = 85, message = "El máximo de caracteres del 'nombre' de Casa es de 85")
        String nombre
) {
}

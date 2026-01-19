package lopez.noa.OrmHarryPotterApp.DTO.VaritaDTO;

import java.math.BigDecimal;

public record VaritaResponseDTO(
        Integer id,
        BigDecimal longitud,
        String madera,
        String nucleo,
        Boolean rota,
        String nombrePersonaje
) {
}

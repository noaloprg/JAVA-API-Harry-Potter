package lopez.noa.OrmHarryPotterApp.DTO.VaritaDTO;

import java.math.BigDecimal;

public record VaritaSummaryDTO(
        int id,
        BigDecimal longitud,
        boolean rota,
        String materiales,
        String personaje
) {
}

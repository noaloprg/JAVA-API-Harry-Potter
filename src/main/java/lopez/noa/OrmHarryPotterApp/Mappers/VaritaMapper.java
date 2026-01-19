package lopez.noa.OrmHarryPotterApp.Mappers;

import lopez.noa.OrmHarryPotterApp.DTO.VaritaDTO.VaritaCreateDTO;
import lopez.noa.OrmHarryPotterApp.DTO.VaritaDTO.VaritaResponseDTO;
import lopez.noa.OrmHarryPotterApp.DTO.VaritaDTO.VaritaSummaryDTO;
import lopez.noa.OrmHarryPotterApp.Modelos.Varita;

public class VaritaMapper {
    public static void asignarTodosCamposVarita(VaritaCreateDTO dto, Varita varita) {
        varita.setLongitud(dto.getLongitud());
        varita.setMadera(dto.getMadera());
        varita.setNucleo(dto.getNucleo());
        varita.setRota(dto.getRota());
    }

    public static Varita crearVaritaDesdeDTO(VaritaCreateDTO dto) {
        Varita varita = new Varita();
        asignarTodosCamposVarita(dto, varita);
        return varita;
    }

    public static VaritaResponseDTO toVaritaResponse(Varita v) {
        return new VaritaResponseDTO(
                v.getId(), v.getLongitud(), v.getMadera(),
                v.getNucleo(), v.getRota(), VaritaMapperHelper.obtenerNombrePerosnaje(v));
    }

    public static VaritaSummaryDTO toVaritaSummary(Varita v) {
        return new VaritaSummaryDTO(
                v.getId(), v.getLongitud(), v.getRota(),
                String.format("%s. %s", v.getMadera(), v.getNucleo()),
                VaritaMapperHelper.obtenerNombrePerosnaje(v)
        );
    }
}

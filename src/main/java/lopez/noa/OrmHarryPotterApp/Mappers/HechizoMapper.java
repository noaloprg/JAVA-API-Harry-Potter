package lopez.noa.OrmHarryPotterApp.Mappers;

import lopez.noa.OrmHarryPotterApp.DTO.HechizoDTO.HechizoCreateDTO;
import lopez.noa.OrmHarryPotterApp.DTO.HechizoDTO.HechizoResponseDTO;
import lopez.noa.OrmHarryPotterApp.Modelos.Hechizo;

public class HechizoMapper {

    public static void asignarTodosCamposHechizo(HechizoCreateDTO dto, Hechizo hechizo) {
        hechizo.setNombre(dto.getNombre());
        hechizo.setDescripcion(dto.getDescripcion());
        hechizo.setTipo(dto.getTipo());
    }

    public static Hechizo crearHechizoDesdeDTO(HechizoCreateDTO dto) {
        Hechizo hechizo = new Hechizo();
        asignarTodosCamposHechizo(dto, hechizo);
        return hechizo;
    }

    public static HechizoResponseDTO toHechizoResponse(Hechizo h) {
        return new HechizoResponseDTO(h.getId(), h.getDescripcion(), h.getNombre(), h.getTipo());
    }

}

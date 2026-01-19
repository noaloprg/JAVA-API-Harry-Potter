package lopez.noa.OrmHarryPotterApp.DTO.PersonajeDTO;

import lopez.noa.OrmHarryPotterApp.DTO.HechizoDTO.HechizoCreateDTO;
import lopez.noa.OrmHarryPotterApp.Modelos.TipoSangre;

import java.time.LocalDate;
import java.util.List;

public class PersonajeHechizoCreateDTO extends PersonajeCreateDTO {
    //lista porque se peude crear un Perosnaje ya con varios hechizos
    private List<HechizoCreateDTO> listahechizos;

    public PersonajeHechizoCreateDTO(LocalDate fechaNacimiento, String nombre, TipoSangre sangre, Integer idCasa, List<HechizoCreateDTO> listahechizos) {
        super(fechaNacimiento, nombre, sangre, idCasa);
        this.listahechizos = listahechizos;
    }

    public List<HechizoCreateDTO> getListahechizos() {
        return listahechizos;
    }

    public void setListahechizos(List<HechizoCreateDTO> listahechizos) {
        this.listahechizos = listahechizos;
    }
}

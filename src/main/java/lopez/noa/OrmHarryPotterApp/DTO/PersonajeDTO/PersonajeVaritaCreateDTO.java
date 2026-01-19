package lopez.noa.OrmHarryPotterApp.DTO.PersonajeDTO;

import lopez.noa.OrmHarryPotterApp.DTO.VaritaDTO.VaritaCreateDTO;
import lopez.noa.OrmHarryPotterApp.Modelos.TipoSangre;

import java.time.LocalDate;
import java.util.List;

//DTO para crear un perosnaje junto a su varita
public class PersonajeVaritaCreateDTO extends PersonajeCreateDTO {

    //lista porque se puede crear un personaje directamente que tenga varias varitas
    private List<VaritaCreateDTO> crearVarita;

    public PersonajeVaritaCreateDTO(LocalDate fechaNacimiento, String nombre, TipoSangre sangre, Integer idCasa, List<VaritaCreateDTO> crearVarita) {
        super(fechaNacimiento, nombre, sangre, idCasa);
        this.crearVarita = crearVarita;
    }

    public PersonajeVaritaCreateDTO(){}

    public List<VaritaCreateDTO> getCrearVarita() {
        return crearVarita;
    }

    public void setCrearVarita(List<VaritaCreateDTO> crearVarita) {
        this.crearVarita = crearVarita;
    }
}

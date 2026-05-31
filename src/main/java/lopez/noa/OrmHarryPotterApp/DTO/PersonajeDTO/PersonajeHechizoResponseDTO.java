package lopez.noa.OrmHarryPotterApp.DTO.PersonajeDTO;

import lopez.noa.OrmHarryPotterApp.DTO.HechizoDTO.HechizoResponseDTO;

import java.util.List;

public class PersonajeHechizoResponseDTO {
    PersonajeResponseDTO personaje;
    List<HechizoResponseDTO> hechizo;

    public PersonajeHechizoResponseDTO(PersonajeResponseDTO personaje, List<HechizoResponseDTO> hechizo) {
        this.personaje = personaje;
        this.hechizo = hechizo;
    }

    public PersonajeHechizoResponseDTO() {
    }

    public PersonajeResponseDTO getPersonaje() {
        return personaje;
    }

    public void setPersonaje(PersonajeResponseDTO personaje) {
        this.personaje = personaje;
    }

    public List<HechizoResponseDTO> getHechizo() {
        return hechizo;
    }

    public void setHechizo(List<HechizoResponseDTO> hechizo) {
        this.hechizo = hechizo;
    }
}

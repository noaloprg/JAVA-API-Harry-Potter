package lopez.noa.OrmHarryPotterApp.DTO.PersonajeDTO;

import lopez.noa.OrmHarryPotterApp.DTO.VaritaDTO.VaritaResponseDTO;

import java.util.List;

public class PersonajeVaritaResponseDTO {
    private PersonajeResponseDTO personaje;
    private List<VaritaResponseDTO> listaVaritas;

    public PersonajeVaritaResponseDTO(PersonajeResponseDTO personaje, List<VaritaResponseDTO> listaVaritas) {
        this.personaje = personaje;
        this.listaVaritas = listaVaritas;
    }

    public PersonajeVaritaResponseDTO() {
    }

    public PersonajeResponseDTO getPersonaje() {
        return personaje;
    }

    public void setPersonaje(PersonajeResponseDTO personaje) {
        this.personaje = personaje;
    }

    public List<VaritaResponseDTO> getListaVaritas() {
        return listaVaritas;
    }

    public void setListaVaritas(List<VaritaResponseDTO> listaVaritas) {
        this.listaVaritas = listaVaritas;
    }
}

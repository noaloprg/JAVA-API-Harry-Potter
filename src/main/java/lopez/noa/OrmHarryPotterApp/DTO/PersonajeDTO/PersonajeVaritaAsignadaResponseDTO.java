package lopez.noa.OrmHarryPotterApp.DTO.PersonajeDTO;

import lopez.noa.OrmHarryPotterApp.DTO.VaritaDTO.VaritaResponseDTO;

// DTO de respuesta al asignar una varita a un personaje
public class PersonajeVaritaAsignadaResponseDTO {
    private int id;
    private String nombrePersonaje;
    private VaritaResponseDTO varita;

    public PersonajeVaritaAsignadaResponseDTO(int id, String nombrePersonaje, VaritaResponseDTO varita) {
        this.id = id;
        this.nombrePersonaje = nombrePersonaje;
        this.varita = varita;
    }


    public PersonajeVaritaAsignadaResponseDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombrePersonaje() {
        return nombrePersonaje;
    }

    public void setNombrePersonaje(String nombrePersonaje) {
        this.nombrePersonaje = nombrePersonaje;
    }

    public VaritaResponseDTO getVarita() {
        return varita;
    }

    public void setVarita(VaritaResponseDTO varita) {
        this.varita = varita;
    }
}

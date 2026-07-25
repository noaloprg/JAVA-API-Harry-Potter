package lopez.noa.OrmHarryPotterApp.DTO.PersonajeDTO;

import lopez.noa.OrmHarryPotterApp.Modelos.TipoSangre;

// DTO de respuesta
public class  PersonajeResponseDTO{
        private Integer id;
        private String nombre;
        private TipoSangre sangre;
        private Integer idCasa;

    public PersonajeResponseDTO(Integer id, String nombre, TipoSangre sangre, Integer idCasa) {
        this.id = id;
        this.nombre = nombre;
        this.sangre = sangre;
        this.idCasa = idCasa;
    }

    public PersonajeResponseDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoSangre getSangre() {
        return sangre;
    }

    public void setSangre(TipoSangre sangre) {
        this.sangre = sangre;
    }

    public Integer getIdCasa() {
        return idCasa;
    }

    public void setIdCasa(Integer idCasa) {
        this.idCasa = idCasa;
    }
}

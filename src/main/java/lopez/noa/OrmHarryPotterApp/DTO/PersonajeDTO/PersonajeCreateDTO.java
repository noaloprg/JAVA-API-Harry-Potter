package lopez.noa.OrmHarryPotterApp.DTO.PersonajeDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lopez.noa.OrmHarryPotterApp.Modelos.TipoSangre;
import java.time.LocalDate;

//DTO para crear a un personaje sin varitas
//no puede ser record porque va a ser heredada
public class PersonajeCreateDTO {
    //LA RELACION CON VARITA Y HECHIZO SE HACE EN UN DTO AUXILIAR

    @Past(message = "La 'fechaNacimiento' debe ser anterior a la actual")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "Debe asignarse un valor al atributo ")
    @Size(max = 50, message = "El máximo de caracteres del 'nombre' de Personaje es de 50")
    private String nombre;

    @NotNull(message = "Debe asignarse un valor al atributo ")
    private TipoSangre sangre;

    @NotNull(message = "Debe asignarse un valor al atributo")
    private Integer idCasa;


    public PersonajeCreateDTO(LocalDate fechaNacimiento, String nombre, TipoSangre sangre, Integer idCasa) {
        this.fechaNacimiento = fechaNacimiento;
        this.nombre = nombre;
        this.sangre = sangre;
        this.idCasa = idCasa;
    }

    public PersonajeCreateDTO() {
    }

    public Integer getIdCasa() {
        return idCasa;
    }

    public void setIdCasa(Integer idCasa) {
        this.idCasa = idCasa;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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
}

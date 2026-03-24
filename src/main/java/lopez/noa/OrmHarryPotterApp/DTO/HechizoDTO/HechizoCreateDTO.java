package lopez.noa.OrmHarryPotterApp.DTO.HechizoDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lopez.noa.OrmHarryPotterApp.Modelos.TipoHechizo;

public class HechizoCreateDTO {
    //LA RELACION CON PERSONAJE SE HACE EN UN DTO AUXILIAR
    @Size(max = 255, message = "El atributo 'descripcion' de Hechizo no puede tener mas de 255 caracteres")
    String descripcion;

    @NotBlank(message = "Debe asignarse un valor al atributo ")
    @Size(max = 35, message = "El atributo 'nombre' de Hechizo no puede tener mas de 35 caracteres")
    String nombre;

    @NotNull(message = "Debe asignarse un valor al atributo")
    TipoHechizo tipo;

    public HechizoCreateDTO(String descripcion, String nombre, TipoHechizo tipo) {
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public HechizoCreateDTO() {
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoHechizo getTipo() {
        return tipo;
    }

    public void setTipo(TipoHechizo tipo) {
        this.tipo = tipo;
    }
}


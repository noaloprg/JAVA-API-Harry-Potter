package lopez.noa.OrmHarryPotterApp.DTO.CasaDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CasaCreateDTO {

    @Size(max = 50, message = "El máximo de caracteres del 'escudoImagen' de Casa es de 50")
    String escudoImagen;

    @NotBlank(message = "Debe asignarse un valor al atributo")
    @Size(max = 85, message = "El máximo de caracteres del 'fundador' de Casa es de 85")
    String fundador;

    @NotBlank(message = "Debe asignarse un valor al atributo")
    @Size(max = 85, message = "El máximo de caracteres del 'nombre' de Casa es de 85")
    String nombre;

    public String getEscudoImagen() {
        return escudoImagen;
    }

    public void setEscudoImagen(String escudoImagen) {
        this.escudoImagen = escudoImagen;
    }

    public String getFundador() {
        return fundador;
    }

    public void setFundador(String fundador) {
        this.fundador = fundador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public CasaCreateDTO(String escudoImagen, String fundador, String nombre) {
        this.escudoImagen = escudoImagen;
        this.fundador = fundador;
        this.nombre = nombre;
    }
    public CasaCreateDTO(){

    }
}

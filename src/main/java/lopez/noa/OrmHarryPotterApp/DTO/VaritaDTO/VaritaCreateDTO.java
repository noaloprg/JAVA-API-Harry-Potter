package lopez.noa.OrmHarryPotterApp.DTO.VaritaDTO;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

//DTO para crear una varita sin asociar a un personaje solo varita
public class VaritaCreateDTO {
    //LA RELACION CON PERSONAJE SE HACE EN UN DTO AUXILIAR

    //para representar la restriccion de precision: 5 - scale: 2
    @DecimalMax(value = "999.99", message = "la longitud no puede ser mayor a 999.99")
    private BigDecimal longitud;

    @NotBlank(message = "No puede estar vacio el atributo")
    @Size(min = 4, max = 150, message = "La longitud de caracteres de 'madera' de Varita es de 4-150")
    private String madera;

    @NotBlank(message = "No puede estar vacio el atributo")
    @Size(min = 5, max = 150, message = "La longitud de caracteres de 'nucleo' de Varita es de 5-150")
    private String nucleo;

    @NotNull(message = "El atributo indicado")
    private Boolean rota;

    public VaritaCreateDTO(BigDecimal longitud, String madera, String nucleo, Boolean rota) {
        this.longitud = longitud;
        this.madera = madera;
        this.nucleo = nucleo;
        this.rota = rota;
    }

    public VaritaCreateDTO() {
    }

    public BigDecimal getLongitud() {
        return longitud;
    }

    public void setLongitud(BigDecimal longitud) {
        this.longitud = longitud;
    }

    public String getMadera() {
        return madera;
    }

    public void setMadera(String madera) {
        this.madera = madera;
    }

    public String getNucleo() {
        return nucleo;
    }

    public void setNucleo(String nucleo) {
        this.nucleo = nucleo;
    }

    public Boolean getRota() {
        return rota;
    }

    public void setRota(Boolean rota) {
        this.rota = rota;
    }
}

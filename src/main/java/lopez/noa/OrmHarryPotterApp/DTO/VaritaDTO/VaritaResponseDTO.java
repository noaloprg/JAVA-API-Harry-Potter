package lopez.noa.OrmHarryPotterApp.DTO.VaritaDTO;

import java.math.BigDecimal;

public class VaritaResponseDTO {
    private Integer id;
    private BigDecimal longitud;
    private String madera;
    private String nucleo;
    private Boolean rota;
    private String nombrePersonaje;

    public VaritaResponseDTO(Integer id, BigDecimal longitud, String madera, String nucleo, Boolean rota, String nombrePersonaje) {
        this.id = id;
        this.longitud = longitud;
        this.madera = madera;
        this.nucleo = nucleo;
        this.rota = rota;
        this.nombrePersonaje = nombrePersonaje;
    }

    public VaritaResponseDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getNombrePersonaje() {
        return nombrePersonaje;
    }

    public void setNombrePersonaje(String nombrePersonaje) {
        this.nombrePersonaje = nombrePersonaje;
    }
}

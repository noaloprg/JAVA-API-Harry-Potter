package lopez.noa.OrmHarryPotterApp.DTO.VaritaDTO;

import java.math.BigDecimal;

// DTO de respuesta resumida
public class VaritaSummaryDTO {
    private int id;
    private BigDecimal longitud;
    private boolean rota;
    private String materiales;
    private String personaje;

    public VaritaSummaryDTO(int id, BigDecimal longitud, boolean rota, String materiales, String personaje) {
        this.id = id;
        this.longitud = longitud;
        this.rota = rota;
        this.materiales = materiales;
        this.personaje = personaje;
    }

    public VaritaSummaryDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getLongitud() {
        return longitud;
    }

    public void setLongitud(BigDecimal longitud) {
        this.longitud = longitud;
    }

    public boolean isRota() {
        return rota;
    }

    public void setRota(boolean rota) {
        this.rota = rota;
    }

    public String getMateriales() {
        return materiales;
    }

    public void setMateriales(String materiales) {
        this.materiales = materiales;
    }

    public String getPersonaje() {
        return personaje;
    }

    public void setPersonaje(String personaje) {
        this.personaje = personaje;
    }
}

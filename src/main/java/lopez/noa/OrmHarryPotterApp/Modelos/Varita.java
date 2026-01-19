package lopez.noa.OrmHarryPotterApp.Modelos;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "varitas")
public class Varita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //tiene que ser bigDecimal para poder usa precision y scale
    @Column(precision = 5, scale = 2, nullable = false)
    private BigDecimal longitud;

    @Column(length = 150, nullable = false)
    private String madera;

    @Column(length = 150, nullable = false)
    private String nucleo;

    @Column(nullable = false)
    private Boolean rota;

    //no tiene porque estar asociado a un personaje por lo que optioanal = true (por defecto)
    @ManyToOne()
    //no hay OrphanRemoval porque puede haber varitas que no esten vinculadas a ningun mago
    @JoinColumn(name = "personaje")
    private Personaje personaje;

    public Varita(BigDecimal longitud, String madera, Integer id, String nucleo, Personaje personaje, Boolean rota) {
        this.longitud = longitud;
        this.madera = madera;
        this.id = id;
        this.nucleo = nucleo;
        this.personaje = personaje;
        this.rota = rota;
    }
    public Varita(){}

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

    public Personaje getPersonaje() {
        return personaje;
    }

    public void setPersonaje(Personaje personaje) {
        this.personaje = personaje;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Varita varita = (Varita) o;
        return Objects.equals(id, varita.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

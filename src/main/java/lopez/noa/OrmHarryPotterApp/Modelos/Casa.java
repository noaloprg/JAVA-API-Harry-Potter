package lopez.noa.OrmHarryPotterApp.Modelos;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "casas")
public class Casa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50)
    private String escudoImagen;

    @Column(length = 85, nullable = false)
    private String fundador;

    //no puede haber 2 casas con el mismo nombre
    @Column(length = 85, nullable = false, unique = true)
    private String nombre;

    /*
    solo actualiza automaticmanete la tabla casa al guardar un personaje o al actualizarlo
    evita que si se elimina una casa se eliminen todos los personajes
     */
    @OneToMany(mappedBy = "casa", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    //inicializar para evitar NullPointer
    private List<Personaje> personajes = new ArrayList<>();

    public Casa(String escudoImagen, String fundador, String nombre, List<Personaje> personajes) {
        this.escudoImagen = escudoImagen;
        this.fundador = fundador;
        this.nombre = nombre;
        this.personajes = personajes;
    }

    public Casa() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public List<Personaje> getPersonajes() {
        return personajes;
    }

    public void setPersonajes(List<Personaje> personajes) {
        this.personajes = personajes;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Casa casa = (Casa) o;
        return Objects.equals(nombre, casa.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nombre);
    }
}

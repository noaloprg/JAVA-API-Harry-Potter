package lopez.noa.OrmHarryPotterApp.Modelos;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "hechizos")
public class Hechizo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false)
    private String descripcion;

    @Column(length = 35, nullable = false, unique = true)
    private String nombre;

    @Column(length = 35, nullable = false)
    private TipoHechizo tipo;

    //para no sobrecargar la clase personaje defino aqui la tabla auxiliar
    @ManyToMany
    @JoinTable(
            name = "personaje_hechizo",
            //tabla desde donde se crea
            joinColumns = @JoinColumn(name = "hechizo_id"),
            //otra tabla de la relacion
            inverseJoinColumns = @JoinColumn(name = "personaje_id")
    )
    //lista de objetos que se vincula a esta clase (java)
    private List<Personaje> personajes = new ArrayList<>();

    public Hechizo(Long id, String descripcion, String nombre, TipoHechizo tipo, List<Personaje> personajes) {
        this.id = id;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.tipo = tipo;
        this.personajes = personajes;
    }
    public Hechizo(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Personaje> getPersonajes() {
        return personajes;
    }

    public void setPersonajes(List<Personaje> personajes) {
        this.personajes = personajes;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Hechizo hechizo = (Hechizo) o;
        return Objects.equals(nombre, hechizo.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nombre);
    }
}

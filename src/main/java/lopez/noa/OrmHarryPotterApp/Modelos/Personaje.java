package lopez.noa.OrmHarryPotterApp.Modelos;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "personajes")
public class Personaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate fechaNacimiento;

    @Column(length = 50, nullable = false)
    private String nombre;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoSangre sangre;

    //no puede haber nulos, no puede haber personajes sin casa en este caso
    @ManyToOne(optional = false)
    @JoinColumn(name = "casa", nullable = false)
    private Casa casa;

    /*
    actualizacion automaticos solo al guardar un pesonaje con una varita o actualizarlo
    una varita puede existir sin un perosnaje, y puede convertirse en la de otro
     */
    @OneToMany(mappedBy = "personaje", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Varita> varitas = new ArrayList<>();

    //puede no saber ningun hechizo
    @ManyToMany(mappedBy = "personajes", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Hechizo> hechizos;

    public Personaje(Integer id, LocalDate fechaNacimiento, String nombre, Casa casa, TipoSangre sangre, List<Varita> varitas, List<Hechizo> hechizos) {
        this.id = id;
        this.fechaNacimiento = fechaNacimiento;
        this.nombre = nombre;
        this.casa = casa;
        this.sangre = sangre;
        this.varitas = varitas;
        this.hechizos = hechizos;
    }
    public Personaje(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Casa getCasa() {
        return casa;
    }

    public void setCasa(Casa casa) {
        this.casa = casa;
    }

    public TipoSangre getSangre() {
        return sangre;
    }

    public void setSangre(TipoSangre sangre) {
        this.sangre = sangre;
    }

    public List<Hechizo> getHechizos() {
        return hechizos;
    }

    public void setHechizos(List<Hechizo> hechizos) {
        this.hechizos = hechizos;
    }

    public List<Varita> getVaritas() {
        return varitas;
    }

    public void setVaritas(List<Varita> varitas) {
        this.varitas = varitas;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Personaje personaje = (Personaje) o;
        return Objects.equals(id, personaje.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

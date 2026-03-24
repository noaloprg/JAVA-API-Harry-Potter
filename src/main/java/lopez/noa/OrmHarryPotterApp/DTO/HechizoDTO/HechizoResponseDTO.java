package lopez.noa.OrmHarryPotterApp.DTO.HechizoDTO;

import lopez.noa.OrmHarryPotterApp.Modelos.TipoHechizo;

public class HechizoResponseDTO {
    private Long id;
    private String descripcion;
    private String nombre;
    private TipoHechizo tipo;
    //no muestra personajes porque muchos personajes van a usar el mismo hechizo


    public HechizoResponseDTO(Long id, String descripcion, String nombre, TipoHechizo tipo) {
        this.id = id;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public HechizoResponseDTO(){}

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
}

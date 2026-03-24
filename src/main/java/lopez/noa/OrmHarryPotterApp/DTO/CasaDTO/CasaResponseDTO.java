package lopez.noa.OrmHarryPotterApp.DTO.CasaDTO;

public class CasaResponseDTO {

    Integer id;
    String escudoImagen;
    String fundador;
    String nombre;
    //no mostrar todos los alumnos que pertenecen por si son muchos


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

    public CasaResponseDTO(Integer id, String escudoImagen, String fundador, String nombre) {
        this.id = id;
        this.escudoImagen = escudoImagen;
        this.fundador = fundador;
        this.nombre = nombre;
    }

    public  CasaResponseDTO(){}
}

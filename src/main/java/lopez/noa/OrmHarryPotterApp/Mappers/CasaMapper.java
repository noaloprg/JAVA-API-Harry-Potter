package lopez.noa.OrmHarryPotterApp.Mappers;

import lopez.noa.OrmHarryPotterApp.DTO.CasaDTO.CasaCreateDTO;
import lopez.noa.OrmHarryPotterApp.DTO.CasaDTO.CasaResponseDTO;
import lopez.noa.OrmHarryPotterApp.Modelos.Casa;

public class CasaMapper {
    /**
     * Mapea los atributos de un DTO a una entidad (asignarTodosCampos...)
     * <p>
     * Asigna todos los campos de un DTO de creacion general a la entidad
     * Su uso permite la reutilziacion tanto en creacion como actualizacion
     * </p>
     *
     * @param dto  Objeto del cual se obtienen los atributos de la entidad
     * @param casa Entidad donde se almacenan los nuevos valores
     */
    public static void asignarTodosCamposCasa(CasaCreateDTO dto, Casa casa) {
        casa.setEscudoImagen(dto.escudoImagen());
        casa.setFundador(dto.fundador());
        casa.setNombre(dto.nombre());
    }

    /*
    la diferencia con el otro es que al crear estas creando un id nuevo y en el otro solo sobreescribiendo datos
     */
    public static Casa crearCasaDesdeDTO(CasaCreateDTO dto) {
        //uso del constructor vacio
        Casa casa = new Casa();
        asignarTodosCamposCasa(dto, casa);
        return casa;
    }

    public static CasaResponseDTO toCasaResponse(Casa c) {
        return new CasaResponseDTO(c.getId(), c.getEscudoImagen(), c.getFundador(), c.getNombre());
    }
}

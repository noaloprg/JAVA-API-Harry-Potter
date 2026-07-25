package lopez.noa.OrmHarryPotterApp.Servicios;

import java.util.List;

/**
 * Interaz donde se definen los metodos que todos los servicios de los modelos deben tener
 *
 * @param <D>  DTO de respuesta de la entidad del servicio que herede la interfaz
 * @param <ID> tipo de dato del ID de la entidad del servicio que herede
 */
public interface IModeloService<D, ID> {
    //SOLO GET Y DELETE PORQUE CREATE Y UPDATE PUEDEN TENER VARIOS DTO

    /**
     * @return lista de DTO de respuesta de la entidad
     */
    List<D> getAll();

    /**
     * 
     * @param id Id por el cual obtener la entidad 
     * @return  DTO de  respuesta de la entidad 
     */
    D getById(ID id);

    /**
     * Igual que {@link IModeloService#getById(Object)}
     */
    D deleteById(ID id);

}

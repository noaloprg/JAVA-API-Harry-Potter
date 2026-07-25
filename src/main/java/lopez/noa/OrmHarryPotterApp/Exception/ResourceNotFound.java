package lopez.noa.OrmHarryPotterApp.Exception;

import java.math.BigInteger;

/**
 * Excepcion para cuando un recurso no existe o no se ha encontrado
 */
public class ResourceNotFound extends RuntimeException {
    private static final String MENSAJE_ERROR = "No se encontro";

    public ResourceNotFound(String message) {
        super(message);
    }

    public ResourceNotFound(String mensaje, Throwable lanzable) {
        super(mensaje, lanzable);
    }
    
    /**
     * @param id      id de la entidad que no se ha encontrado
     * @param entidad nombre de la entidad que se mostrara en el mensaje, para poder retulizar
     */
    public ResourceNotFound(BigInteger id, String entidad) {
        super(String.format("%s %s con ID %d", MENSAJE_ERROR, entidad, id));
    }


}

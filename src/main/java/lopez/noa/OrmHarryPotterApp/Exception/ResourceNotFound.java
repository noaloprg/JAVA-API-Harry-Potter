package lopez.noa.OrmHarryPotterApp.Exception;

import java.math.BigInteger;

public class ResourceNotFound extends RuntimeException {
    private static final String MENSAJE_ERROR = "No se encontro";

    //POR DEFECTO
    public ResourceNotFound(String message) {
        super(message);
    }
    public ResourceNotFound(String mensaje, Throwable lanzable) {
        super(mensaje, lanzable);
    }

    //MENSAJE PERSONALIZADO SEGUN ID
    public ResourceNotFound(Integer id, String entidad) {
        super(String.format("%s %s con ID %d", MENSAJE_ERROR, entidad, id));
    }
    public ResourceNotFound(BigInteger id, String entidad) {
        super(String.format("%s %s con ID %d", MENSAJE_ERROR, entidad, id));
    }


}

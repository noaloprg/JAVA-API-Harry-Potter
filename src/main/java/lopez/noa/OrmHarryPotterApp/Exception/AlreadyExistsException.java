package lopez.noa.OrmHarryPotterApp.Exception;

// Excepcion de que el recurso ya existe
public class AlreadyExistsException extends RuntimeException {

    public AlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyExistsException(String entidad){
        super(String.format("%s ya existe", entidad));
    }
}

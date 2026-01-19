package lopez.noa.OrmHarryPotterApp.Exception;

public class AlreadyExistsException extends RuntimeException {

    public AlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyExistsException(String entidad){
        super(String.format("%s ya existe", entidad));
    }
}

package lopez.noa.OrmHarryPotterApp.Exception;

public class BrokenWandException extends RuntimeException {
    public BrokenWandException(String message) {
        super(message);
    }

    public BrokenWandException(Integer id) {
        super(String.format("La varita con ID %d esta rota, no se puede asignar", id));
    }
}

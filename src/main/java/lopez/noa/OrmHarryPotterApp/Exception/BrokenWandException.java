package lopez.noa.OrmHarryPotterApp.Exception;

// Excepcion para cuando un varita se intenta asignar pero rota == true
public class BrokenWandException extends RuntimeException {
    public BrokenWandException(String message) {
        super(message);
    }

    public BrokenWandException(Integer id) {
        super(String.format("La varita con ID %d esta rota, no se puede asignar", id));
    }
}

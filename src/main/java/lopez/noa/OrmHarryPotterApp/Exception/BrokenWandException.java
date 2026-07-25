package lopez.noa.OrmHarryPotterApp.Exception;

/**
 * Excepcion para cuando un {@link lopez.noa.OrmHarryPotterApp.Modelos.Varita} se intenta asignar pero {@code rota == true}
 */
public class BrokenWandException extends RuntimeException {
    public BrokenWandException(String message) {
        super(message);
    }

    public BrokenWandException(Integer id) {
        super(String.format("La varita con ID %d esta rota, no se puede asignar", id));
    }
}

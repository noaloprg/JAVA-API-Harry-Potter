package lopez.noa.OrmHarryPotterApp.Exception;

public class AlreadyAssignedExcepction extends RuntimeException {
    public AlreadyAssignedExcepction(String message) {
        super(message);
    }

    //para relaciones 1:N, donde N se relaciona con 1
    public AlreadyAssignedExcepction(Integer id, String entidad1, String entidad2) {
        super(String.format("Entidad %s con ID %d ya esta relacionada con una entidad %s", entidad1, id, entidad2));
    }
}

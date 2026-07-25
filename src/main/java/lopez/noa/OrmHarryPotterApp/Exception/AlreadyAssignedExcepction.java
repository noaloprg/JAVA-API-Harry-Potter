package lopez.noa.OrmHarryPotterApp.Exception;

// Excepcion de relacion ya existente
//para relaciones 1:N, donde N se relaciona con 1
public class AlreadyAssignedExcepction extends RuntimeException {
    public AlreadyAssignedExcepction(String message) {
        super(message);
    }

    public AlreadyAssignedExcepction(Integer id, String entidad1, String entidad2) {
        super(String.format("Entidad %s con ID %d ya esta relacionada con una entidad %s", entidad1, id, entidad2));
    }
}

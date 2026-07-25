package lopez.noa.OrmHarryPotterApp.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
/**
 * Controlador de excepciones de la app
 */
public class HarryPotterExceptionHandler {
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<Map<String, String>> noSeEncontroRegistro(ResourceNotFound e) {
        Map<String, String> errores = new HashMap<>();
        errores.put("Error", "Resource not found");
        errores.put("Message", e.getMessage());
        return ResponseEntity.badRequest().body(errores);
    }


    @ExceptionHandler(AlreadyAssignedExcepction.class)
    public ResponseEntity<Map<String, String>> asignacionYaExistente(AlreadyAssignedExcepction e) {
        Map<String, String> errores = new HashMap<>();

        errores.put("Error", "Asignacion ya existente");
        errores.put("Message", e.getMessage());
        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(BrokenWandException.class)
    // Varita no asignable porque esta rota
    public ResponseEntity<Map<String, String>> varitaRota(BrokenWandException e) {
        Map<String, String> errores = new HashMap<>();

        errores.put("Error", "Varita rota");
        errores.put("Message", e.getMessage());

        return ResponseEntity.badRequest().body(errores);
    }


    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> recursoYaExiste(AlreadyExistsException e) {
        Map<String, String> errores = new HashMap<>();

        errores.put("Error", "Recurso ya existente");
        errores.put("Message", e.getMessage());
        return ResponseEntity.badRequest().body(errores);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    /**
     * se lanza cuando una atributo con alguna etiqueta de validacion no es cumple
     * @return mensaje del error
     */
    public ResponseEntity<String> manejarValidacion(MethodArgumentNotValidException ex) {
        String mensaje = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                //convierte la lista de FieldError en lista de String
                .map(error -> error.getField() + ": " + error.getDefaultMessage() + "\n")
                //une todos los elementos de la lista string en uno solo
                .collect(Collectors.joining());
        return ResponseEntity.badRequest().body(mensaje);
    }
}

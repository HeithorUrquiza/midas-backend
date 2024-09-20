package ifg.midas.infra.exception;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.TransientPropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> handleEntityNotFound() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<String> handleEntityExist() { return ResponseEntity.badRequest().body("Usuário já cadastrado"); }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationDataError>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<FieldError> errors = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(errors.stream().map(ValidationDataError::new).toList());
    }

    private record ValidationDataError(String field, String message) {
        public ValidationDataError(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<DataIntegrityError> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return ResponseEntity.badRequest().body(new DataIntegrityError(ex));
    }

    private record DataIntegrityError(String message) {
        public DataIntegrityError(DataIntegrityViolationException ex) {
            this(ex.getMessage());
        }
    }

    @ExceptionHandler(TransientPropertyValueException.class)
    public ResponseEntity<HashMap<String, String>> handleTransientPropertyValueException(TransientPropertyValueException ex) {
        HashMap<String, String> error = new HashMap<>(Map.of(
                "propertyName", ex.getPropertyName(),
                "message", ex.getMessage()));
        return ResponseEntity.badRequest().body(error);
    }
}
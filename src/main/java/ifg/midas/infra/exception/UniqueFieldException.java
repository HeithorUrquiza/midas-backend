package ifg.midas.infra.exception;

import lombok.Getter;

@Getter
public class UniqueFieldException extends RuntimeException {
    private String field;

    public UniqueFieldException(String message) {
        super(message);
    }

    public UniqueFieldException(String field, String message) {
        super(message);
        this.field = field;
    }
}

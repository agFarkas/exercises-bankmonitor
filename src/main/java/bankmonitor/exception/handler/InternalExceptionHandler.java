package bankmonitor.exception.handler;

import bankmonitor.exception.NoEntityFoundException;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InternalExceptionHandler {

    @ExceptionHandler(NoEntityFoundException.class)
    public ResponseEntity<String> handleNoEntityFoundException(NoEntityFoundException ex) {
        return ResponseEntity.badRequest()
                .body(ex.getMessage());
    }

    @ExceptionHandler(JSONException.class)
    public ResponseEntity<String> handleJSONException(JSONException ex) {
        return ResponseEntity.badRequest()
                .body(ex.getMessage());
    }

}

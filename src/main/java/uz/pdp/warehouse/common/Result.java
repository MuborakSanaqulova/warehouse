package uz.pdp.warehouse.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@JsonInclude(content = JsonInclude.Include.NON_EMPTY)
public class Result {

    private String message;
    private boolean success;
    private Object object;

    public Result(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public Result(String message, boolean success, Object object) {
        this.message = message;
        this.success = success;
        this.object = object;
    }

    public Result() {
    }
}

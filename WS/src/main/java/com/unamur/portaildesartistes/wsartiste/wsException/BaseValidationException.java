package com.unamur.portaildesartistes.wsartiste.wsException;

/*
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
*/

/*
@Data // Lombok : generate getter and setter (other @Getter or @Setter)
@EqualsAndHashCode(callSuper = false) // Lombok : generate hascode and equals
@AllArgsConstructor // Lombok : generate a constructor with all parameters
*/
public  class BaseValidationException extends BaseSubException {

    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }

    public void setRejectedValue(Object rejectedValue) {
        this.rejectedValue = rejectedValue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    BaseValidationException(String object, String message) {
        this.setObject(object);
        this.setMessage(message);
    }

    BaseValidationException(String object, String field, Object rejectedValue, String message) {
        this.setObject(object);
        this.setField(field);
        this.setRejectedValue(rejectedValue);
        this.setMessage(message);
    }

}



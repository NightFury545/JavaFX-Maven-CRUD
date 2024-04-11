package org.nightfury.persistence.repository.exception;

public class AlreadyExistsException extends Throwable {

    private String message;

    public AlreadyExistsException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

package com.ultimate.cards.exception;

public class CardAlreadyExistsException extends RuntimeException {
    /**
     * @param message
     */
    public CardAlreadyExistsException(String message) {
        super(message);
    }

}

package com.barbearia.losbrabos.exceptions;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(){ super("Token expirado."); }
    public InvalidTokenException(String message) { super(message); }
}

package com.barbearia.losbrabos.exceptions;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(){ super("Senha antiga não corresponde."); }
    public InvalidPasswordException(String message){ super(message); }
}

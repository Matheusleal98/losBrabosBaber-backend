package com.barbearia.losbrabos.exceptions;

public class UserFoundException extends RuntimeException {

    public UserFoundException() { super("Usuário já existe"); }
    public UserFoundException(String message) { super(message); }
}

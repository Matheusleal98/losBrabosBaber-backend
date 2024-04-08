package com.barbearia.losbrabos.exceptions;

public class InvalidDateException extends RuntimeException {

    public InvalidDateException() { super("Não é possível criar um agendamento em uma data passada."); }

    public InvalidDateException(String message) { super(message); }
}

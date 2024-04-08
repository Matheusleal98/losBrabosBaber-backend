package com.barbearia.losbrabos.exceptions;

public class DateNotFoundException extends RuntimeException {

    public DateNotFoundException() { super("Você só pode criar agendamento das 8h às 17h"); }

    public DateNotFoundException(String message) { super(message); }
}

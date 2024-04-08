package com.barbearia.losbrabos.exceptions;

public class AppointmentBookedException extends RuntimeException {

    public AppointmentBookedException() { super("Este agendamento já está reservado"); }

    public AppointmentBookedException(String message) { super(message); }
}

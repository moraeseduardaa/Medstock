package com.aep.medstock.exception;

public class EstoqueException extends RuntimeException {

    public EstoqueException() {
        super("Estoque insuficiente");
    }

}
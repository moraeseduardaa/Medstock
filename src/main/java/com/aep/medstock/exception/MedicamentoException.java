package com.aep.medstock.exception;

public class MedicamentoException extends RuntimeException {
    public MedicamentoException(String id) {

        super("Medicamento não encontrado id: " + id);
    }



}
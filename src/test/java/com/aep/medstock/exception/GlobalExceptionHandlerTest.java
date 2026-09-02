package com.aep.medstock.exception;

import com.aep.medstock.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler =
            new GlobalExceptionHandler();

    @Test
    void deveTratarMedicamentoNaoEncontrado() {

        MedicamentoException exception =
                new MedicamentoException("123");

        ResponseEntity<Map<String, String>> response =
                handler.handleNotFound(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        assertNotNull(response.getBody());

        assertEquals(
                "Medicamento não encontrado id: 123",
                response.getBody().get("erro")
        );
    }

    @Test
    void deveTratarEstoqueInsuficiente() {

        EstoqueException exception =
                new EstoqueException();

        ResponseEntity<Map<String, String>> response =
                handler.handleEstoqueInsuficiente(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        assertNotNull(response.getBody());

        assertEquals(
                "Estoque insuficiente",
                response.getBody().get("erro")
        );
    }

    @Test
    void deveTratarErroDeValidacao() {

        BindingResult bindingResult =
                mock(BindingResult.class);

        FieldError erroNome =
                new FieldError(
                        "medicamentoCreate",
                        "nome",
                        "Nome é obrigatório"
                );

        FieldError erroQuantidade =
                new FieldError(
                        "medicamentoCreate",
                        "quantidade",
                        "Quantidade é obrigatória"
                );

        when(bindingResult.getFieldErrors())
                .thenReturn(List.of(erroNome, erroQuantidade));

        MethodArgumentNotValidException exception =
                mock(MethodArgumentNotValidException.class);

        when(exception.getBindingResult())
                .thenReturn(bindingResult);

        ResponseEntity<Map<String, Object>> response =
                handler.handleValidation(exception);

        assertEquals(
                HttpStatus.BAD_REQUEST,
                response.getStatusCode()
        );

        assertNotNull(response.getBody());

        Map<String, String> fieldErrors =
                (Map<String, String>) response.getBody().get("fieldErrors");

        assertNotNull(fieldErrors);

        assertEquals(
                "Nome é obrigatório",
                fieldErrors.get("nome")
        );

        assertEquals(
                "Quantidade é obrigatória",
                fieldErrors.get("quantidade")
        );

        assertEquals(2, fieldErrors.size());
    }
}

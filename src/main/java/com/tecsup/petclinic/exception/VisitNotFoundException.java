package com.tecsup.petclinic.exception;

/**
 * Excepción lanzada cuando una visita no es encontrada.
 *
 * @author jgomezm
 *
 */
public class VisitNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public VisitNotFoundException(String message) {
        super(message);
    }
}
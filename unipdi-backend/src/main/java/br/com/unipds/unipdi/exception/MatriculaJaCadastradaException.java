package br.com.unipds.unipdi.exception;

public class MatriculaJaCadastradaException extends RuntimeException {
    public MatriculaJaCadastradaException(String matricula) {
        super("Matricula já cadastrada: " + matricula);
    }
}

package br.com.fiap.metaflow.exception;

public class RegraDeNegocioVioladaException extends RuntimeException {
  public RegraDeNegocioVioladaException(String mensagem) {
    super(mensagem);
  }
}

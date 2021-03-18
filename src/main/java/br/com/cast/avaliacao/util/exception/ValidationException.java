package br.com.cast.avaliacao.util.exception;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 8458827579061562018L;

	public ValidationException(String mensagem) {
		super(mensagem);
	}

	public ValidationException(Object id, String nome) {
		super(String.format("%s de identificador '%d' não encontrado.", nome, id));
	}

	public ValidationException(Object id, String nome, String coluna) {
		super(String.format("%s de %s '%d' não encontrado.", nome, coluna, id));
	}

}

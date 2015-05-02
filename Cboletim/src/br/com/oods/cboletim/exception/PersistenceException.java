package br.com.oods.cboletim.exception;

/**
 * Componente de exceção para operações de persistência.
 * 
 * <p>A ideia aqui é utilizar uma <code>RuntimeException</code> para encapsular <code>SQLException</code>.</p>
 * 
 * @author Vinicius
 */

public class PersistenceException extends RuntimeException {

	public PersistenceException(String msg) {
		super(msg);
	}
	
	public PersistenceException(Exception ex) {
		super(ex);
	}
	
	public PersistenceException(String msg, Exception ex) {
		super(msg, ex);
	}
	
}

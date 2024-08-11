package org.nirz.reservationApp.Exception;

public class BusNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public BusNotFoundException(String message) {
		super(message);
	}
}

package com.okman.litemq.exception;

/**
 * key�Ѵ���Exception
 *
 * @auth waxuan
 * @since 2018��7��19������11:00:10
 */
public class KeyAleadyExistException extends Exception {

	private static final long serialVersionUID = 3746214078323301420L;

	public KeyAleadyExistException(){
        super();
    }

    public KeyAleadyExistException(String message){
        super(message);
    }

    public KeyAleadyExistException(String message, Throwable cause){
        super(message,cause);
    }

     public KeyAleadyExistException(Throwable cause) {
         super(cause);
     }
	
}

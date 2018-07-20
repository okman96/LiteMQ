package com.okman.litemq.exception;

/**
 * 为注入异步类Exception
 *
 * @auth waxuan
 * @since 2018年7月19日下午3:01:35
 */
public class ExecutorNotInjectException extends Exception {

	private static final long serialVersionUID = 3746214078323301420L;

	public ExecutorNotInjectException(){
        super();
    }

    public ExecutorNotInjectException(String message){
        super(message);
    }

    public ExecutorNotInjectException(String message, Throwable cause){
        super(message,cause);
    }

     public ExecutorNotInjectException(Throwable cause) {
         super(cause);
     }
	
}

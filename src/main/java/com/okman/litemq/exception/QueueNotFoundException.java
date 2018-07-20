package com.okman.litemq.exception;

/**
 * 队列未找到Exception
 *
 * @auth waxuan
 * @since 2018年7月19日下午3:01:35
 */
public class QueueNotFoundException extends Exception {

	private static final long serialVersionUID = 3746214078323301420L;

	public QueueNotFoundException(){
        super();
    }

    public QueueNotFoundException(String message){
        super(message);
    }

    public QueueNotFoundException(String message, Throwable cause){
        super(message,cause);
    }

     public QueueNotFoundException(Throwable cause) {
         super(cause);
     }
	
}

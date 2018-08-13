/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

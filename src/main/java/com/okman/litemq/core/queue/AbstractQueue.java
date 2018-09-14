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
package com.okman.litemq.core.queue;

import java.util.concurrent.Executor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.okman.litemq.core.element.IElement;
import com.okman.litemq.exception.ExecutorNotInjectException;
import com.okman.litemq.exception.KeyAleadyExistException;

/**
 * litemq先进先出队列
 *
 * @auth waxuan
 * @since 2018年8月22日下午5:34:05
 */
public abstract class AbstractQueue extends java.util.AbstractQueue<IElement> implements IQueue<IElement> {

	private static final Log logger = LogFactory.getLog(AbstractQueue.class);
	
	/**
	 * 异步类
	 */
	protected Executor executor;
	
	/**
	 * 是否已开启遍历模式
	 */
	protected boolean isLoop;
	
	/**
	 * 队列在工厂中的key
	 */
	protected String key;
	
	/**
	 * 线程锁
	 */
	protected Byte[] lock = new Byte[1];
	
	public AbstractQueue(String key) throws KeyAleadyExistException {
		this.key = key;
	}
	
	public boolean getLoopState() {
		return isLoop;
	}

	public String getKey() {
		return this.key;
	}
	
	public void setExecutor(Executor executor) {
		this.executor = executor;
	}

	@Override
	public void startLoop() throws ExecutorNotInjectException {
		if (executor == null) {
			throw new ExecutorNotInjectException("未注入Executor");
		}
		if (isLoop) {
			logger.warn("###### " + key + " is already startLoop! ######");
		}
		isLoop = true;
		executor.execute(new Runnable() {
			public void run() {
	        	while(isLoop) {
	    			await();
	    			if (!isEmpty()) {
	    				loopExcute();
	    			}
	    		}
	        }
		});
	}
	
	
	@Override
	public void stopLoop() {
		this.isLoop = false;
	}
	
	@Override
    public void await() {
    	synchronized(lock) {
			if (this.size() == 0) {
				logger.debug("###### " + getKey() + " is empty, waiting...... ######");
				try {
					lock.wait();
				} catch (InterruptedException e) {
					logger.error("###### await() error ######", e);
				}
			}
		}
    }
    
    @Override
    public void await(long time) {
    	synchronized(lock) {
    		logger.debug("###### " + key + " is waiting " + time + " millisecond   ######");
			try {
				lock.wait(time);
			} catch (InterruptedException e) {
				logger.error("###### await(long time) error ######", e);
			}
		}
    }
    
    /**
     * 执行遍历业务
     *
     * @auth waxuan
     * @since 2018年7月19日下午3:18:44
     */
    public abstract void loopExcute();
    
    /**
     * 取出元素后的操作
     * 
     * @auth waxuan
     * @since 2018年7月19日下午3:19:11
     * @param o
     * @return
     */
    public abstract void afterPeek(IElement o);
    
}

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

import java.util.PriorityQueue;
import java.util.concurrent.Executor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.okman.litemq.core.element.IElement;
import com.okman.litemq.exception.ExecutorNotInjectException;
import com.okman.litemq.exception.KeyAleadyExistException;

/**
 * litemq优先队列
 *
 * @auth waxuan
 * @since 2018年7月19日下午3:22:45
 */
public abstract class AbstractPriorityQueue extends PriorityQueue<IElement> implements IQueue<IElement> {

	private static final long serialVersionUID = -6259230902061199971L;

	private static final Log logger = LogFactory.getLog(AbstractPriorityQueue.class);
	
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
	
	public AbstractPriorityQueue(String key) throws KeyAleadyExistException {
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
	
	public void startLoop() throws ExecutorNotInjectException {
		if (executor == null) {
			throw new ExecutorNotInjectException("未注入Executor");
		}
		if (isLoop) {
			logger.info("###### " + key + " is already startLoop! ######");
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
	
	/**
	 * 重写offer(O o)方法
	 */
	@Override
    public boolean offer(IElement e) {
		synchronized(lock) {
    		lock.notifyAll();
            return super.offer(e);
    	}
    }

	public void stopLoop() {
		this.isLoop = false;
	}
	
    public void await() {
    	synchronized(lock) {
			if (this.size() == 0) {
				logger.info("###### " + getKey() + " is empty, waiting...... ######");
				try {
					lock.wait();
				} catch (InterruptedException e) {
					logger.error("###### await error ######", e);
				}
			}
		}
    }
    
    public void await(long time) {
    	synchronized(lock) {
    		logger.info("###### " + key + " is waiting " + time + " millisecond ...,  ######");
			try {
				lock.wait(time);
			} catch (InterruptedException e) {
				logger.error("###### await error ######", e);
			}
		}
    }
    
    /**
     * 执行遍历业务
     *
     * @auth waxuan
     * @since 2018年7月19日下午3:18:44
     */
    private void loopExcute() {
    	IElement o = poll();
    	if (!afterPeek(o)) {
    		reoffer(o);
    	} 
    }
    
    /**
     * 取出元素后的操作
     * 
     * <p>如果取出元素后不再需要该元素则返回true</p>
     * <p>如果取出元素后仍再需要该元素则返回false，接着会执行repush(IElement o)方法</p>
     * 
     * @auth waxuan
     * @since 2018年7月19日下午3:19:11
     * @param o
     * @return
     */
    public abstract boolean afterPeek(IElement o);
    
    /**
     * 重新放入元素
     * 
     * <p>放入操作需要实现类进行实现</p>
     *
     * @auth waxuan
     * @since 2018年7月19日下午3:21:13
     * @param o
     */
    public abstract void reoffer(IElement o);
    
}

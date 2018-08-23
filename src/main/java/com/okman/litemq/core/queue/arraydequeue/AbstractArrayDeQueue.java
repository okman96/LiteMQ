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
package com.okman.litemq.core.queue.arraydequeue;

import java.io.File;
import java.util.ArrayDeque;
import java.util.Iterator;

import com.okman.litemq.Config;
import com.okman.litemq.core.element.IElement;
import com.okman.litemq.core.queue.AbstractQueue;
import com.okman.litemq.exception.KeyAleadyExistException;
import com.okman.litemq.persistence.Persistence;

/**
 * litemq优先队列
 *
 * @auth waxuan
 * @since 2018年7月19日下午3:22:45
 */
public abstract class AbstractArrayDeQueue extends AbstractQueue {
	
	private ArrayDeque<IElement> arrayDeque = new ArrayDeque<IElement>();
	
	public final static String PERSISTENCE_PREFIX = "arrayDeque-";
	
	public AbstractArrayDeQueue(String key) throws KeyAleadyExistException {
		super(key);
	}
    
	/**
     * 执行遍历业务
     *
     * @auth waxuan
     * @since 2018年7月19日下午3:18:44
     */
	@Override
    public void loopExcute() {
    	IElement e = poll();
		afterPeek(e);
		if (Config.getInstance().getIsPersistence()) {
			File file = new File(Config.getInstance().getPersistenceDir(), PERSISTENCE_PREFIX + e.getIndex() + Config.getInstance().getPersistenceSuffix());
			file.delete();
		}
    }
	
	/**
	 * 重写offer(O o)方法
	 */
	@Override
    public boolean offer(IElement e) {
		synchronized(lock) {
			arrayDeque.offer(e);
			lock.notifyAll();
    		if (Config.getInstance().getIsPersistence()) {
    			Persistence.getInstance().save(PERSISTENCE_PREFIX, e);
    		}
            return true;
    	}
    }

	@Override
	public IElement remove() {
		return arrayDeque.remove();
	}

	@Override
	public IElement poll() {
		return arrayDeque.poll();
	}

	@Override
	public IElement element() {
		return arrayDeque.element();
	}

	@Override
	public IElement peek() {
		return arrayDeque.peek();
	}
	
	@Override
	public Iterator<IElement> iterator() {
		return arrayDeque.iterator();
	}

	@Override
	public int size() {
		return arrayDeque.size();
	}
	
	public boolean offerFirst(IElement e) {
		synchronized(lock) {
			arrayDeque.offerFirst(e);
			lock.notifyAll();
    		if (Config.getInstance().getIsPersistence()) {
    			Persistence.getInstance().save(PERSISTENCE_PREFIX, e);
    		}
            return true;
    	}
	}
	
	public boolean offerLast(IElement e) {
		synchronized(lock) {
			arrayDeque.offerLast(e);
			lock.notifyAll();
    		if (Config.getInstance().getIsPersistence()) {
    			Persistence.getInstance().save(PERSISTENCE_PREFIX, e);
    		}
            return true;
    	}
	}
	
	@Override
	public String getPersistencePrefix() {
		return PERSISTENCE_PREFIX;
	}
	
}

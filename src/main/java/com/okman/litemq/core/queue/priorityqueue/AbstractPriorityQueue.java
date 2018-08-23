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
package com.okman.litemq.core.queue.priorityqueue;

import java.io.File;
import java.util.Iterator;
import java.util.PriorityQueue;

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
public abstract class AbstractPriorityQueue extends AbstractQueue {
	
	private PriorityQueue<IElement> priorityQueue = new PriorityQueue<IElement>();
	
	public final static String PERSISTENCE_PREFIX = "priorityQueue-";
	
	public AbstractPriorityQueue(String key) throws KeyAleadyExistException {
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
    	long difference = e.getIndex() - System.currentTimeMillis();
    	if (difference <= 0) {
    		afterPeek(e);
    		if (Config.getInstance().getIsPersistence()) {
    			File file = new File(Config.getInstance().getPersistenceDir(), PERSISTENCE_PREFIX + e.getIndex() + Config.getInstance().getPersistenceSuffix());
    			file.delete();
    		}
    	} else {
    		offer(e);
    		await(difference);
    	}
    }
	
	/**
	 * 重写offer(O o)方法
	 */
	@Override
    public boolean offer(IElement e) {
		synchronized(lock) {
			priorityQueue.offer(e);
			lock.notifyAll();
    		if (Config.getInstance().getIsPersistence()) {
    			Persistence.getInstance().save(PERSISTENCE_PREFIX, e);
    		}
            return true;
    	}
    }

	@Override
	public boolean add(IElement e) {
		return priorityQueue.add(e);
	}

	@Override
	public IElement remove() {
		return priorityQueue.remove();
	}

	@Override
	public IElement poll() {
		return priorityQueue.poll();
	}

	@Override
	public IElement element() {
		return priorityQueue.element();
	}

	@Override
	public IElement peek() {
		return priorityQueue.peek();
	}
	
	@Override
	public Iterator<IElement> iterator() {
		return priorityQueue.iterator();
	}

	@Override
	public int size() {
		return priorityQueue.size();
	}
	
	@Override
	public String getPersistencePrefix() {
		return PERSISTENCE_PREFIX;
	}
	
}

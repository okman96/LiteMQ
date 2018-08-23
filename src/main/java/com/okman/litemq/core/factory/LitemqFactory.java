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
package com.okman.litemq.core.factory;

import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import com.okman.litemq.core.element.IElement;
import com.okman.litemq.core.queue.IQueue;
import com.okman.litemq.exception.ExecutorNotInjectException;
import com.okman.litemq.exception.KeyAleadyExistException;
import com.okman.litemq.exception.QueueNotFoundException;
import com.okman.litemq.persistence.Persistence;

/**
 * 有先队列工厂
 *
 * @auth waxuan
 * @since 2018年7月19日下午2:55:11
 */
public class LitemqFactory implements ILitemqFactory {
	
	/**
	 * 存储队列集合
	 */
	private Map<String, IQueue<IElement>> queueBucket = new LinkedHashMap<String, IQueue<IElement>>();
	
	/**
	 * 是否已经加载过持久化
	 */
	private boolean loadPersistenced = false;
	
	public LitemqFactory() {
		
	}
	
	public LitemqFactory(String qualifiedQueueClassName, String key) throws KeyAleadyExistException {
		this.create(qualifiedQueueClassName, key);
	}
	
	public LitemqFactory(String qualifiedQueueClassName, List<String> keys) throws KeyAleadyExistException {
		this.create(qualifiedQueueClassName, keys);
	}
	
	public LitemqFactory(String qualifiedQueueClassName, String key, Executor executor) throws KeyAleadyExistException, ExecutorNotInjectException {
		this.createAndLoop(qualifiedQueueClassName, key, executor);
	}
	
	public LitemqFactory(String qualifiedQueueClassName, List<String> keys, Executor executor) throws KeyAleadyExistException, ExecutorNotInjectException {
		this.createAndLoop(qualifiedQueueClassName, keys, executor);
	}

	@Override
	public Map<String, IQueue<IElement>> getQueueBucket() {
		return this.queueBucket;
	}
	
	@Override
	public IQueue<IElement> createAndLoop(String qualifiedQueueClassName, String key, Executor executor) throws KeyAleadyExistException, ExecutorNotInjectException {
		if (this.queueBucket.containsKey(key)) {
			throw new KeyAleadyExistException("该key已存在");
		}
		IQueue<IElement> queue = this.create(qualifiedQueueClassName, key);
		
		/**
		 * 启动推送持久化中的元素
		 */
		if (!loadPersistenced) {
			Persistence.getInstance().initLoad(queue, queue.getPersistencePrefix());
			loadPersistenced = true;
		}
		
		queue.setExecutor(executor);
		queue.startLoop();
		return queue;
	}
	
	@Override
	public void createAndLoop(String qualifiedQueueClassName, List<String> keys, Executor executor) throws KeyAleadyExistException, ExecutorNotInjectException {
		for (String key : keys) {
			this.createAndLoop(qualifiedQueueClassName, key, executor);
		}
	}
	
	@Override
	public IQueue<IElement> create(String qualifiedQueueClassName, String key) throws KeyAleadyExistException {
		if (this.queueBucket.containsKey(key)) {
			throw new KeyAleadyExistException("该key已存在");
		}
		IQueue<IElement> queue = this.createQueueInReflect(qualifiedQueueClassName, key);
		this.queueBucket.put(key, queue);
		return queue;
	}
	
	@Override
	public void create(String qualifiedQueueClassName, List<String> keys) throws KeyAleadyExistException {
		for (String key : keys) {
			this.create(qualifiedQueueClassName, key);
		}
	}
	
	@Override
	public IQueue<IElement> findQueue(String key) {
		return this.queueBucket.get(key);
	}
	
	/**
	 * 通过反射创建Queue
	 *
	 * @auth waxuan
	 * @since 2018年7月19日下午3:10:26
	 * @param qualifiedQueueClassName
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private IQueue<IElement> createQueueInReflect(String qualifiedQueueClassName, String key) {
		Class<?> classObj = null;
		IQueue<IElement> queue = null;
        try {
            classObj = Class.forName(qualifiedQueueClassName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
        	Constructor<?> constructor = classObj.getConstructor(String.class);  
        	queue = (IQueue<IElement>) constructor.newInstance(key); 
         } catch(Exception e) {
            e.printStackTrace();
         }
         return queue;
	}

	@Override
	public void removeQueue(String key) throws QueueNotFoundException {
		IQueue<IElement> queue = this.queueBucket.remove(key);
		if (queue == null) {
			throw new QueueNotFoundException("队列不存在");
		}
		queue.stopLoop();
	}

	@Override
	public void startLoop(String key) throws QueueNotFoundException, ExecutorNotInjectException {
		IQueue<IElement> queue = findQueue(key);
		if (queue == null) {
			throw new QueueNotFoundException("队列不存在");
		}
		queue.startLoop();
	}

	@Override
	public void stopLoop(String key) throws QueueNotFoundException {
		IQueue<IElement> queue = findQueue(key);
		if (queue == null) {
			throw new QueueNotFoundException("队列不存在");
		}
		queue.stopLoop();
	}

	@Override
	public void startAllLoop() {
		for (Map.Entry<String, IQueue<IElement>> entry : this.queueBucket.entrySet()) {
			try {
				entry.getValue().startLoop();
			} catch (Exception e) {
				System.err.println(e);
			}
			
        }
	}

	@Override
	public void stopAllLoop() {
		for (Map.Entry<String, IQueue<IElement>> entry : this.queueBucket.entrySet()) {
			entry.getValue().stopLoop();
        }
	}

}

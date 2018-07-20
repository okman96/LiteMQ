package com.okman.litemq.core.factory;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.Executor;

import com.okman.litemq.core.element.IElement;
import com.okman.litemq.core.queue.IQueue;
import com.okman.litemq.exception.ExecutorNotInjectException;
import com.okman.litemq.exception.KeyAleadyExistException;
import com.okman.litemq.exception.QueueNotFoundException;

/**
 * litemq队列工厂
 *
 * @auth waxuan
 * @since 2018年7月19日下午2:55:11
 */
public class LiteMQFactory implements ILiteMQFactory {
	
	/**
	 * 工厂放入元素的计数器
	 */
	private int counter;
	
	/**
	 * 优先队列
	 */
	private Map<String, IQueue<? extends IElement>> queueMap = new LinkedHashMap<String, IQueue<? extends IElement>>();

	public IQueue<? extends IElement> createAndLoop(String qualifiedQueueClassName, String key, Executor executor) throws KeyAleadyExistException, ExecutorNotInjectException {
		if (queueMap.containsKey(key)) {
			throw new KeyAleadyExistException("该key已存在");
		}
		IQueue<? extends IElement> queue = this.createQueueInReflect(qualifiedQueueClassName, key);
		queueMap.put("name", queue);
		queue.setExecutor(executor);
		queue.startLoop();
		return queue;
	}
	
	public void createAndLoop(String qualifiedQueueClassName, List<String> keys, Executor executor) throws KeyAleadyExistException, ExecutorNotInjectException {
		for (String key : keys) {
			this.createAndLoop(qualifiedQueueClassName, key, executor);
		}
	}
	
	public IQueue<? extends IElement> create(String qualifiedQueueClassName, String key) throws KeyAleadyExistException {
		if (queueMap.containsKey(key)) {
			throw new KeyAleadyExistException("该key已存在");
		}
		IQueue<? extends IElement> queue = this.createQueueInReflect(qualifiedQueueClassName, qualifiedQueueClassName);
		queueMap.put("name", queue);
		return queue;
	}
	
	public void create(String qualifiedQueueClassName, List<String> keys) throws KeyAleadyExistException {
		for (String key : keys) {
			this.create(qualifiedQueueClassName, key);
		}
	}
	
	public IQueue<? extends IElement> findQueue(String key) {
		return queueMap.get(key);
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
	private IQueue<? extends IElement> createQueueInReflect(String qualifiedQueueClassName, String key) {
		Class<?> classObj = null;
		IQueue<? extends IElement> queue = null;
        try {
            classObj = Class.forName(qualifiedQueueClassName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
        	Constructor<?> constructor = classObj.getConstructor(String.class);  
        	queue = (IQueue<? extends IElement>) constructor.newInstance(key); 
         } catch(Exception e) {
            e.printStackTrace();
         }
         return queue;
	}

	public void removeQueue(String key) throws QueueNotFoundException {
		IQueue<? extends IElement> queue = queueMap.remove(key);
		if (queue == null) {
			throw new QueueNotFoundException("队列不存在");
		}
		queue.stopLoop();
	}

	public void startLoop(String key) throws QueueNotFoundException, ExecutorNotInjectException {
		IQueue<? extends IElement> queue = findQueue(key);
		if (queue == null) {
			throw new QueueNotFoundException("队列不存在");
		}
		queue.startLoop();
	}

	public void stopLoop(String key) throws QueueNotFoundException {
		IQueue<? extends IElement> queue = findQueue(key);
		if (queue == null) {
			throw new QueueNotFoundException("队列不存在");
		}
		queue.stopLoop();
	}

	public void startAllLoop() {
		for (Map.Entry<String, IQueue<? extends IElement>> entry : queueMap.entrySet()) {
			try {
				entry.getValue().startLoop();
			} catch (Exception e) {
			}
			
        }
	}

	public void stopAllLoop() {
		for (Map.Entry<String, IQueue<? extends IElement>> entry : queueMap.entrySet()) {
			entry.getValue().stopLoop();
        }
	}
	
	public synchronized void randomOffer(IElement e) {
		
	}
	
	public synchronized void loopOffer(IElement e) {
		counter++;
		int queueSize = queueMap.size();
		ListIterator<IQueue<IElement>> it = new ArrayList<?>(queueMap.entrySet()).listIterator(queueSize);
        while(i.hasPrevious()) {
            Map.Entry entry=i.previous();
            System.out.println("key:" + entry.getKey() + ",value:" + entry.getValue());
        }
	}

}

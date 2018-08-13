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
package com.okman.litemq.util;

import java.util.Map;

import com.okman.litemq.core.element.IElement;
import com.okman.litemq.core.factory.ILitemqFactory;
import com.okman.litemq.core.queue.IQueue;
import com.okman.litemq.exception.ExecutorNotInjectException;
import com.okman.litemq.exception.QueueNotFoundException;

/**
 * Litemq工具类
 *
 * @auth waxuan
 * @since 2018年7月19日下午2:48:30
 */
public class LitemqHelper {
	
	/**
	 * 返回队列当前元素个数
	 *
	 * @auth waxuan
	 * @since 2018年7月20日上午11:49:04
	 * @param key		队列key
	 * @param factory	工厂
	 * @return		队列key或者工厂不存在返回-1
	 */
	public static int getElementSize(ILitemqFactory factory, String key) {
		if (factory == null) return -1;
		IQueue<?> queue = factory.findQueue(key);
		if (queue == null) return -1;
		return queue.size();
	}
	
	/**
	 * 返回队列当前元素个数
	 *
	 * @auth waxuan
	 * @since 2018年7月20日上午11:49:04
	 * @param factory	工厂
	 * @return		工厂不存在返回-1
	 */
	public static int getElementSize(ILitemqFactory factory) {
		if (factory == null) return -1;
		int count = 0;
		for (Map.Entry<String, IQueue<IElement>> entry : factory.getQueueBucket().entrySet()) {
			count += entry.getValue().size();
        }
		return count;
	}
	
	/**
	 * 开启队列的便利模式
	 *
	 * @auth waxuan
	 * @since 2018年7月19日下午3:03:07
	 * @param key
	 * 					队列key
	 * @throws QueueNotFoundException
	 * @throws ExecutorNotInjectException
	 */
	public static void startLoop(ILitemqFactory factory, String key) throws QueueNotFoundException, ExecutorNotInjectException {
		IQueue<IElement> queue = factory.findQueue(key);
		if (queue == null) {
			throw new QueueNotFoundException("队列不存在");
		}
		queue.startLoop();
	}

	/**
	 * 停止队列遍历模式
	 *
	 * @auth waxuan
	 * @since 2018年7月19日下午3:08:43
	 * @param key
	 * @throws QueueNotFoundException
	 * @throws ExecutorNotInjectException
	 */
	public static void stopLoop(ILitemqFactory factory, String key) throws QueueNotFoundException {
		IQueue<? extends IElement> queue = factory.findQueue(key);
		if (queue == null) {
			throw new QueueNotFoundException("队列不存在");
		}
		queue.stopLoop();
	}

	/**
	 * 开启所有队列遍历模式
	 * @throws ExecutorNotInjectException 
	 *
	 * @auth waxuan
	 * @since 2018年7月19日下午3:08:58
	 */
	public static void startAllLoop(ILitemqFactory factory) throws ExecutorNotInjectException {
		for (Map.Entry<String, IQueue<IElement>> entry : factory.getQueueBucket().entrySet()) {
			entry.getValue().startLoop();
		}
	}

	/**
	 * 停止所有队列遍历模式
	 *
	 * @auth waxuan
	 * @since 2018年7月19日下午3:08:58
	 */
	public static void stopAllLoop(ILitemqFactory factory) {
		for (Map.Entry<String, IQueue<IElement>> entry : factory.getQueueBucket().entrySet()) {
			entry.getValue().stopLoop();
        }
	}
	
	/**
	 * 通过工厂放入元素
	 *
	 * <p>可解决多队列时，随机放入的场景</p>
	 *
	 * @auth waxuan
	 * @since 2018年7月20日下午1:48:16
	 * @param factory
	 * @param e
	 */
	public static void randomOffer(ILitemqFactory factory, IElement e) {
		MapUtil.getRandomValueFromMap(factory.getQueueBucket()).offer(e);
	}
	
}

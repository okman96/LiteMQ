
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

import java.util.List;
import java.util.Map;

import com.okman.litemq.core.element.IElement;
import com.okman.litemq.core.queue.IQueue;
import com.okman.litemq.exception.ExecutorNotInjectException;
import com.okman.litemq.exception.KeyAleadyExistException;
import com.okman.litemq.exception.QueueNotFoundException;

/**
 * liteMQ队列工厂接口
 *
 * @auth waxuan
 * @since 2018年7月19日下午2:57:04
 */
public interface ILitemqFactory {

	/**
	 * 每个实现类活抽象类，都需要拥有一份集合，用来存储其创建的队列
	 *
	 * @auth waxuan
	 * @since 2018年7月20日下午1:44:10
	 * @return
	 */
	Map<String, IQueue<IElement>> getQueueBucket();
	
	/**
	 * 批量创建队列并开启遍历模式
	 *
	 * @auth waxuan
	 * @since 2018年7月19日下午3:40:04
	 * @param qualifiedQueueClassName
	 * 						队列类的全限定名称
	 * @param keys
	 * 						队列key
	 * @return
	 * @throws KeyAleadyExistException
	 * @throws ExecutorNotInjectException
	 */
	void createAndLoop(String qualifiedQueueClassName, List<String> keys) throws KeyAleadyExistException, ExecutorNotInjectException;
	
	/**
	 * 批量创建队列
	 *
	 * @auth waxuan
	 * @since 2018年7月19日下午3:43:46
	 * @param qualifiedQueueClassName
	 * @param keys
	 * @throws KeyAleadyExistException
	 */
	void create(String qualifiedQueueClassName, List<String> keys) throws KeyAleadyExistException;
	
	/**
	 * 查找已创建的队列
	 *
	 * @auth waxuan
	 * @since 2018年7月19日下午2:58:35
	 * @param key
	 * 					队列key
	 * @return
	 * 			如果未找到则返回null
	 */
	IQueue<IElement> findQueue(String key);
	
	/**
	 * 从工厂中删除队列，并停止队列遍历
	 *
	 * <p>如果工厂中不存在该key的队列，则抛出QueueNotFoundException</p>
	 * 
	 * @auth waxuan
	 * @since 2018年7月19日下午3:01:51
	 * @param key
	 * @throws QueueNotFoundException
	 */
	void removeQueue(String key) throws QueueNotFoundException;
	
	/**
	 * 开启队列的遍历模式
	 *
	 * @auth waxuan
	 * @since 2018年7月19日下午3:03:07
	 * @param key
	 * 					队列key
	 * @throws QueueNotFoundException
	 * @throws ExecutorNotInjectException
	 */
	void startLoop(String key) throws QueueNotFoundException, ExecutorNotInjectException;
	
	/**
	 * 停止队列遍历模式
	 *
	 * @auth waxuan
	 * @since 2018年7月19日下午3:08:43
	 * @param key
	 * @throws QueueNotFoundException
	 * @throws ExecutorNotInjectException
	 */
	void stopLoop(String key) throws QueueNotFoundException, ExecutorNotInjectException;
	
	/**
	 * 开启所有队列遍历模式
	 *
	 * @auth waxuan
	 * @since 2018年7月19日下午3:08:58
	 */
	void startAllLoop() throws ExecutorNotInjectException ;
	
	/**
	 * 停止所有队列遍历模式
	 *
	 * @auth waxuan
	 * @since 2018年7月19日下午3:08:58
	 */
	void stopAllLoop();
	
}

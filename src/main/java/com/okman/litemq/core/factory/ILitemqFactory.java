package com.okman.litemq.core.factory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

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
	 * 创建队列并开启遍历模式
	 *
	 * @auth waxuan
	 * @since 2018年7月19日下午2:57:14
	 * @param qualifiedQueueClassName
	 * 						队列类的全限定名称
	 * @param key
	 * 						队列key
	 * @param executor
	 * 						异步类
	 * @return
	 * @throws KeyAleadyExistException
	 * @throws ExecutorNotInjectException
	 */
	IQueue<IElement> createAndLoop(String qualifiedQueueClassName, String key, Executor executor) throws KeyAleadyExistException, ExecutorNotInjectException;
	
	/**
	 * 批量创建队列并开启遍历模式
	 *
	 * @auth waxuan
	 * @since 2018年7月19日下午3:40:04
	 * @param qualifiedQueueClassName
	 * 						队列类的全限定名称
	 * @param keys
	 * 						队列key
	 * @param executor
	 * 						异步类
	 * @return
	 * @throws KeyAleadyExistException
	 * @throws ExecutorNotInjectException
	 */
	void createAndLoop(String qualifiedQueueClassName, List<String> keys, Executor executor) throws KeyAleadyExistException, ExecutorNotInjectException;
	
	/**
	 * 创建队列
	 *
	 * @auth waxuan
	 * @since 2018年7月19日下午2:57:54
	 * @param qualifiedQueueClassName
	 * 								队列类的全限定名称
	 * @param key
	 * 								队列key
	 * @return
	 * @throws Exception
	 */
	IQueue<IElement> create(String qualifiedQueueClassName, String key) throws KeyAleadyExistException;

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
	 * 开启队列的便利模式
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

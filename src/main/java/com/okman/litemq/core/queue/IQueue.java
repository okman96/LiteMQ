package com.okman.litemq.core.queue;

import java.util.concurrent.Executor;

import com.okman.litemq.exception.ExecutorNotInjectException;

/**
 * litemq队列接口
 *
 * @auth waxuan
 * @since 2018年7月19日下午3:15:30
 */
public interface IQueue<IElement> extends java.util.Queue<IElement> {

	/**
	 * 获取遍历模式状态
	 *
	 * @auth waxuan
	 * @since 2018年7月19日下午3:12:07
	 * @return
	 * 				true:开启遍历模式
	 * 				false:未开启遍历模式
	 */
	boolean getLoopState();
	
	/**
	 * 开启遍历模式
	 *
	 * @auth waxuan
	 * @since 2018年7月19日下午3:15:38
	 * @throws ExecutorNotInjectException
	 */
	void startLoop() throws ExecutorNotInjectException;
	
	/**
	 * 停止遍历模式
	 *
	 * @auth waxuan
	 * @since 2018年7月19日下午3:12:46
	 * @return
	 */
	void stopLoop();
	
	/**
	 * 获取该队列在工厂内的key
	 *
	 * @auth waxuan
	 * @since 2018年7月19日下午3:13:34
	 * @return
	 */
	String getKey();
	
	/**
	 * 设置异步类
	 *
	 * @auth waxuan
	 * @since 2018年7月19日下午3:13:05
	 * @param executor
	 */
	void setExecutor(Executor executor);
	
	/**
	 * 等待
	 *
	 * @auth waxuan
	 * @since 2018年7月19日下午3:13:53
	 */
	void await();
	
	/**
	 * 等待
	 *
	 * @auth waxuan
	 * @since 2018年7月19日下午3:14:05
	 * @param time		单位：毫秒
	 */
	void await(long time);

}

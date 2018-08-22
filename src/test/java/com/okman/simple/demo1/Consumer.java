package com.okman.simple.demo1;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.okman.litemq.core.element.IElement;
import com.okman.litemq.core.queue.AbstractPriorityQueue;
import com.okman.litemq.exception.KeyAleadyExistException;

/**
 * 队列的业务逻辑
 * 
 * 需继承AbstractPriorityQueue，并重写afterPeek
 *
 * @auth waxuan
 * @since 2018年8月20日上午11:01:59
 */
public class Consumer extends AbstractPriorityQueue {

	private static final Log logger = LogFactory.getLog(AbstractPriorityQueue.class);
	
	private static final long serialVersionUID = -5134005198019549797L;


	public Consumer(String key) throws KeyAleadyExistException {
		super(key);
	}
	
	/**
     * 取出元素后的操作
     * 
     * @auth waxuan
     * @since 2018年7月19日下午3:19:11
     * @param e
     * @return
     */
	@Override
	public void afterPeek(IElement e) {
		try {
			Product product = (Product)e;
			System.out.println("应发送时间:" + DateUtil.dateToStr(new Date(e.getIndex())) + ",当前时间为：" + DateUtil.dateToStr(new Date(System.currentTimeMillis())) + ",业务处理，pop元素：" + product.getName());
		} catch (Exception ex) {
			logger.error("###### afterPeek ######", ex);
		}
	}
}

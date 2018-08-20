package com.okman.simple.demo1;

import com.okman.litemq.core.element.IElement;
import com.okman.litemq.core.queue.AbstractPriorityQueue;
import com.okman.litemq.exception.KeyAleadyExistException;

/**
 * 队列的业务逻辑
 * 
 * 需继承AbstractPriorityQueue，并重写afterPeek和reoffer两个方法
 *
 * @auth waxuan
 * @since 2018年8月20日上午11:01:59
 */
public class Consumer extends AbstractPriorityQueue {

	
	private static final long serialVersionUID = -5134005198019549797L;


	public Consumer(String key) throws KeyAleadyExistException {
		super(key);
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
	@Override
	public boolean afterPeek(IElement e) {
		try {
			Product product = (Product)e;
			
			if (product.getIndex() < 0) {
				return false;
			}
		} catch (Exception ex) {
			
		}
		return true;
	}

	/**
     * 重新放入元素
     * 
     * <p>放入操作需要实现类进行实现</p>
     *
     * @auth waxuan
     * @since 2018年7月19日下午3:21:13
     * @param o
     */
	@Override
	public void reoffer(IElement o) {
		System.out.println(o);
	}

}

package com.okman.simple.demo1;

import com.okman.litemq.core.element.IElement;
import com.okman.litemq.core.queue.AbstractPriorityQueue;
import com.okman.litemq.exception.KeyAleadyExistException;

public class Consumer extends AbstractPriorityQueue {

	private static final long serialVersionUID = -5134005198019549797L;


	public Consumer(String key) throws KeyAleadyExistException {
		super(key);
	}
	
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

	@Override
	public void reoffer(IElement o) {
		System.out.println(o);
	}

}

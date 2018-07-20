package com.okman.test;

import com.okman.litemq.core.element.IElement;
import com.okman.litemq.core.queue.AbstractPriorityQueue;
import com.okman.litemq.exception.KeyAleadyExistException;

public class Consumer extends AbstractPriorityQueue {

	public Consumer(String key) throws KeyAleadyExistException {
		super(key);
	}

	private static final long serialVersionUID = -3401592490270098257L;

	
	@Override
	public boolean afterPeek(IElement e) {
		try {
			Thread.sleep(1000);
			Product product = (Product)e;
			System.out.println(this.key + ":" + product.getName());
		} catch (Exception ex) {
			
		}
		return true;
	}


	@Override
	public void reoffer(IElement o) {
		
	}

}

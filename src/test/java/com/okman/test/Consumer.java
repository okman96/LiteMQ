package com.okman.test;

import com.okman.litemq.core.element.IElement;
import com.okman.litemq.core.queue.AbstractPriorityQueue;
import com.okman.litemq.exception.KeyAleadyExistException;

public class TestQueue extends AbstractPriorityQueue {

	public TestQueue(String key) throws KeyAleadyExistException {
		super(key);
	}

	private static final long serialVersionUID = -3401592490270098257L;

	
	@Override
	public boolean afterPeek(IElement o) {
		try {
			Thread.sleep(500l);
		} catch (Exception e) {
		}
		
		return true;
	}


	@Override
	public void reoffer(IElement o) {
		
	}

}

package com.okman.test;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.log4j.PropertyConfigurator;

import com.okman.litemq.core.factory.ILiteMQFactory;
import com.okman.litemq.core.factory.LiteMQFactory;
import com.okman.litemq.core.queue.IQueue;

public class Test {

	public static void main(String[] args) throws Exception {
		PropertyConfigurator.configure("d://log4j.properties");  
		
		Executor executor1 = Executors.newSingleThreadExecutor();
		Executor executor2 = Executors.newSingleThreadExecutor();
		
		ILiteMQFactory factory = new LiteMQFactory();
		@SuppressWarnings("unchecked")
		final IQueue<TestElement> queue = (IQueue<TestElement>) factory.createAndLoop("com.waxuan.litemq.test.TestQueue", "order1", executor1);
		
		executor2.execute(new Runnable() {
			public void run() {
				TestElement e = new TestElement();
				e.setIndex(System.currentTimeMillis() + 10000);
				queue.offer(e);
	        }
		});
		
	}
	
}

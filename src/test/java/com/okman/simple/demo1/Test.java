package com.okman.simple.demo1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.log4j.PropertyConfigurator;

import com.okman.litemq.core.factory.ILitemqFactory;
import com.okman.litemq.core.factory.LitemqFactory;
import com.okman.litemq.util.LitemqHelper;

public class Test {

	public static void main(String[] args) throws Exception {
		String path = Test.class.getResource("/").getPath();
		PropertyConfigurator.configure(path +"com/okman/simple/log4j.properties");  
		
		/**
		 * 创建线程池
		 */
		int taskSize = 5;
		Executor executor1 = Executors.newFixedThreadPool(taskSize);
		
		/**
		 * 创建消费者，并开启遍历模式
		 */
		List<String> keys = new ArrayList<String>();
		keys.add("order1");
		keys.add("order2");
		keys.add("order3");
		keys.add("order4");
		ILitemqFactory factory = new LitemqFactory("com.okman.simple.demo1.Consumer", keys, executor1);
		
		/**
		 * 创建生产者，创建产品
		 */
		executor1.execute(new Runnable() {
			public void run() {
				for (int i=0;i<10;i++) {
					try {
						Thread.sleep(200);
					} catch (Exception e) {
						System.out.println(e);
					}
					Product product = new Product();
					product.setIndex(i);
					product.setName("名称-" + i);
					product.setPrice(Long.valueOf(i + 20 + ""));
					LitemqHelper.randomOffer(factory, product);
				}
				System.out.println("等待一会，再放入数据......");
				try {
					Thread.sleep(2000);
				} catch (Exception e) {
					System.out.println(e);
				}
				System.out.println("继续放入数据。。。。。");
				for (int i=0;i<10;i++) {
					try {
						Thread.sleep(200);
					} catch (Exception e) {
						System.out.println(e);
					}
					Product product = new Product();
					product.setIndex(i);
					product.setName("名称-" + i);
					product.setPrice(Long.valueOf(i + 20 + ""));
					LitemqHelper.randomOffer(factory, product);
				}
	        }
		});
	}
	
}

## 什么是LiteMQ
* LiteMQ是一款简单的队列处理开源项目
* 使用java语言实现，无需搭建中间件服务，减少运维成本，适合中小型项目，为加快项目开发节奏而生
* 含有丰富的api，调用简单

## LiteMQ有哪些功能
* 实现优先队列
    *  自定义数据pop顺序
    *  自定义数据pop时间
* 队列自动pop
* 支持异步多线程pop
* 支持队列中元素的抽象化
* 支持多队列同时工作
	*  随机队列put
	*  指定队列

## 兼容性
* jdk1.7以上

## 使用说明

* 由于未上传至maven，因此需要将项目check后进行打包处理
```
	cd $LiteMQ_HOME$
	mvn clean package
```
* 依赖pom
```
	<!-- LiteMQ -->
	<dependency>
	  	<groupId>com.okman</groupId>
  		<artifactId>liteMQ</artifactId>
  		<version>1.0.1</version>
	</dependency>
```
* 正式编写代码
在/src/test/java的com.okman.simple.demo1包

	*  Consumer：队列的业务逻辑
	*	Product：队列中元素的抽象对象
	*  Test：主程序入口
 
>Consumer.java

```javascript
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
```

>Product.java

```javascript
package com.okman.simple.demo1;

import com.okman.litemq.core.element.IElement;

/**
 * 产品实体
 * 
 * 需实现IElement接口
 *
 * @auth waxuan
 * @since 2018年7月20日下午1:34:41
 */
public class Product implements IElement {
    
	/**
	 * 产品生产编号
	 */
	private long index;
	
	/**
	 * 产品名称
	 */
	private String name;
	
	/**
	 * 产品价格
	 */
	private long price;
	
	/* 
	 * 自定义排序方式
	 * 一般情况下请使用index属性进行排序
	 */
	public int compareTo(IElement o) {
		if(this.getIndex() >= o.getIndex())
            return 1;
        else 
            return -1;
	}

	public void setIndex(long index) {
		this.index = index;
	}

	public long getIndex() {
		return index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}
	
}
```

>Test.java

```javascript
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

```
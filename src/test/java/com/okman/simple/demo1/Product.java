package com.okman.simple.demo1;

import java.io.Serializable;

import com.okman.litemq.core.element.IElement;

/**
 * 产品实体
 * 
 * 需实现IElement接口
 *
 * @auth waxuan
 * @since 2018年7月20日下午1:34:41
 */
public class Product implements IElement, Serializable{
	
	private static final long serialVersionUID = 2565276049357086111L;

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

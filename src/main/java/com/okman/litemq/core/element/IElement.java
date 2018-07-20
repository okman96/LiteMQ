package com.okman.litemq.core.element;

/**
 * 队列中元素接口
 *
 * @auth waxuan
 * @since 2018年7月19日下午3:11:10
 */
public interface IElement extends Comparable<IElement> {

	/**
	 * 设置编号
	 *
	 * @auth waxuan
	 * @since 2018年7月19日下午3:11:37
	 * @param index
	 */
	public void setIndex(long index);
	
	/**
	 * 获取编号
	 *
	 * @auth waxuan
	 * @since 2018年7月19日下午3:11:44
	 * @return
	 */
	public long getIndex();

}

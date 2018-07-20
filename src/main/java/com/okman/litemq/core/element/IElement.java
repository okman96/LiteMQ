/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

package com.okman.litemq.core.element;

import java.io.Serializable;

public abstract class AbstractElement implements IElement, Serializable {

	private static final long serialVersionUID = 4097875351022321381L;

	/** 
	 * 自定义排序方式
	 */
	@Override
	public int compareTo(IElement o) {
		if(this.getIndex() >= o.getIndex())
            return 1;
        else 
            return -1;
	}

}

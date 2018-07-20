package com.okman.test;

import com.okman.litemq.core.element.IElement;

public class TestElement implements IElement {
	
	private long index;
	
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
	
	


}

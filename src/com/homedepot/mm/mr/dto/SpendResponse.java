package com.homedepot.mm.mr.dto;

import java.io.Serializable;
import java.util.List;

public class SpendResponse implements Serializable
{

	private static final long serialVersionUID = -5438135548279140796L;
	
	private List<TopDeptSpendTO> depts;

	public List<TopDeptSpendTO> getDepts() {
		return depts;
	}

	public void setDepts(List<TopDeptSpendTO> depts) {
		this.depts = depts;
	}

	
}

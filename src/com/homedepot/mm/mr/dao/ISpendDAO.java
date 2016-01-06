package com.homedepot.mm.mr.dao;

import java.util.List;

import com.homedepot.mm.mr.dto.TopDeptSpendTO;
import com.homedepot.ta.aa.dao.exceptions.QueryException;

public interface ISpendDAO {
	
	public List<TopDeptSpendTO> getSpendByDepartment(String customerId,String startDate,String endDate,String langCode)  throws QueryException; 

}

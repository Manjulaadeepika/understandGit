package com.homedepot.mm.mr.bl;

import com.homedepot.mm.mr.dto.SpendResponse;
import com.homedepot.ta.aa.dao.exceptions.QueryException;

public interface ISpendManager {
	
	public SpendResponse getSpendByDept(String customerId,String startDate,String endDate,String langCode) throws QueryException;

}

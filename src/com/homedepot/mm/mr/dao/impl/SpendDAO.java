package com.homedepot.mm.mr.dao.impl;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.homedepot.mm.mr.dao.ISpendDAO;
import com.homedepot.mm.mr.dto.TopDeptSpendTO;
import com.homedepot.mm.mr.utility.DAOQueryConstants;

import com.homedepot.mm.mr.utility.Util;
import com.homedepot.ta.aa.dao.builder.DAO;
import com.homedepot.ta.aa.dao.exceptions.QueryException;

@Component
public class SpendDAO implements ISpendDAO {

	private static final Logger LOGGER = Logger.getLogger(SpendDAO.class);
	
	@Override
	public List<TopDeptSpendTO> getSpendByDepartment(String customerId,String startDate,String endDate,String langCode) throws QueryException
	{
		
		if(LOGGER.isDebugEnabled())
		{
			LOGGER.debug("Entering the method getSpendByDepartment in SpendDAO");
		}
		
		AtomicInteger index = new AtomicInteger(1);
		
		List<TopDeptSpendTO> topSpendByDeptList = DAO.useJNDI(DAOQueryConstants.PR4_JNDI)
												   .setSQL(DAOQueryConstants.GET_TOP_SPEND_DEPT)
												   .input(index.getAndIncrement(), customerId)
												   .input(index.getAndIncrement(), langCode)
												   .input(index.getAndIncrement(), Util.getSQLDateinMMDDYYYYFromString(startDate))
												   .input(index.get(), Util.getSQLDateinMMDDYYYYFromString(endDate))
												   .debug(LOGGER)
												   .displayAs("getSpendByDepartment")
												   .list(TopDeptSpendTO.class);
		
		if(LOGGER.isDebugEnabled())
		{
			LOGGER.debug("Leaving the method getSpendByDepartment in SpendDAO");
		}
		
		
		return topSpendByDeptList;
	}
}

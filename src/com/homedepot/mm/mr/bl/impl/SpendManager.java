package com.homedepot.mm.mr.bl.impl;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.homedepot.mm.mr.bl.ISpendManager;
import com.homedepot.mm.mr.dao.impl.SpendDAO;
import com.homedepot.mm.mr.dto.SpendResponse;
import com.homedepot.mm.mr.dto.TopDeptSpendTO;
import com.homedepot.mm.mr.utility.DAOQueryConstants;
import com.homedepot.ta.aa.dao.exceptions.QueryException;

@Component
public class SpendManager implements ISpendManager {
	private static final Logger LOGGER = Logger.getLogger(SpendManager.class);

	@Autowired
	private SpendDAO spendDAO;

	private LinkedHashMap<Integer, String> departmentMap = new LinkedHashMap<Integer, String>();

	@Override
	public SpendResponse getSpendByDept(String customerId, String startDate,
			String endDate, String langCode) throws QueryException {

		SpendResponse spendResponse = new SpendResponse();
		
		List<TopDeptSpendTO> departmentsWithSpend = null;
		
		float totalSpend = 0;
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Entering the manager method getSpendByDept in SpendManager");
		}

		//Read the spend by department from the database
		List<TopDeptSpendTO> departmentsSpendData = spendDAO
				.getSpendByDepartment(customerId, startDate, endDate, langCode);


		// Get the spend for the departments which are pro related.
		departmentsWithSpend = getDepartmentsWithSpend(departmentsSpendData);
		
		// Get the grand total spend for the all the departments 
		totalSpend = getTotalSpend(departmentsWithSpend);
		
		// Calculate the percentage of each department w.r.t to grand total 
		if(totalSpend > 0)
			spendResponse.setDepts(calculateDeptSpendPercentage(departmentsWithSpend,totalSpend));
		
		return spendResponse;

	}
	
	private float getTotalSpend( List<TopDeptSpendTO> spendList) {
		float total = 0;
		for (int index=0;index < 5 && index < spendList.size() ; index++) {
			total += spendList.get(index).getTotalSpend();
		}
		return total;
	}
	
	/**
	 * Calculates the dept spend percentage, taking first item as max
	 * 
	 * @param spendList
	 * @return
	 */
	private List<TopDeptSpendTO> calculateDeptSpendPercentage(List<TopDeptSpendTO> spendList,float total ) {
		List<TopDeptSpendTO> updatedListWithTop5Dept=new ArrayList<TopDeptSpendTO>();	
		for (int index=0; index < 5 && index < spendList.size() ; index++)
			{
				
				spendList.get(index).setTotalPercentage((int)(spendList.get(index).getTotalSpend() / total * 100));
				updatedListWithTop5Dept.add(spendList.get(index));
			}
			
		return updatedListWithTop5Dept;
	}

	private void initializeDepartmentMap(LinkedHashMap<Integer, String> departmentMap) {
		// Setting the list of depts to return the spend for in the
		// homepage/business tool page
		for (int i = 0; i < DAOQueryConstants.DEPARTMENT_NUMBERS.length; i++) {
			departmentMap.put(DAOQueryConstants.DEPARTMENT_NUMBERS[i], DAOQueryConstants.DEPARTMENT_NAMES[i]);
		}
	}

	private List<TopDeptSpendTO> getDepartmentsWithSpend(
			List<TopDeptSpendTO> departmentsSpendData) {
		
		initializeDepartmentMap(this.departmentMap);
		
		List<TopDeptSpendTO> departmentsWithSpend = new ArrayList<TopDeptSpendTO>();
		for (TopDeptSpendTO topDeptSpendTO : departmentsSpendData) {
			if (this.departmentMap.containsKey(Integer.parseInt(topDeptSpendTO
					.getDeptNumber())) && topDeptSpendTO.getTotalSpend() > 0) {
				departmentsWithSpend.add(topDeptSpendTO);
			}
		}
		return departmentsWithSpend;
	}

}

package com.homedepot.mm.mr.dto;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.homedepot.ta.aa.dao.builder.DAOElement;

public class TopDeptSpendTO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@DAOElement({"DEPT_NBR"})
	private String deptNumber;
	@DAOElement({"DEPT_NM"})
	private String deptName;
	@DAOElement({"TOTAL_TRANS_AMT"})
	private int totalSpend;
	private int totalPercentage;
	
	public String getDeptNumber()
	{
		return deptNumber;
	}
	public void setDeptNumber(String deptNumber)
	{
		this.deptNumber = deptNumber;
	}
	public String getDeptName()
	{
		return deptName;
	}
	
	
	public void setDeptName(String deptName)
	{
		this.deptName = StringUtils.trim(deptName);
	}
	public int getTotalSpend()
	{
		return totalSpend;
	}
	public void setTotalSpend(int totalSpend)
	{
		this.totalSpend = totalSpend;
	}
	public int getTotalPercentage()
	{
		return totalPercentage;
	}
	public void setTotalPercentage(int totalPercentage)
	{
		this.totalPercentage = totalPercentage;
	}
}
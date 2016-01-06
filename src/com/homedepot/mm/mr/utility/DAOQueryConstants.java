package com.homedepot.mm.mr.utility;

public final class DAOQueryConstants {

	public static final String PR4_JNDI = "java:comp/env/jdbc/DB2Z.PR4.001"; 
	
	public static final String GET_TOP_SPEND_DEPT =
		"SELECT a.mer_dept_nbr AS DEPT_NBR, " + 
		       "c.dept_nm AS DEPT_NM, " +
		       "Sum(a.item_mer_amt) " + 
		       "+ Sum(Coalesce(a.tot_rwrd_disc_amt, 0)) " + 
		       "+ Sum(Coalesce(a.tot_nrwrd_disc_amt, 0)) AS TOTAL_TRANS_AMT " + 
		"FROM   cust_trans_dtl a, " +
		       "cust_trans b, " + 
		       "nls_dept c " + 
		"WHERE  a.sls_dt = b.sls_dt " + 
		       "AND a.str_nbr = b.str_nbr " + 
		       "AND a.rgstr_nbr = b.rgstr_nbr " + 
		       "AND a.pos_trans_id = b.pos_trans_id " + 
		       "AND a.mer_dept_nbr = c.dept_nbr " +
		       "AND b.cust_id = ? " +
		       "AND c.lang_cd = ? " +
		       "AND b.sls_dt >= ? " +
		       "AND b.sls_dt <= ? " +
		"GROUP  BY a.mer_dept_nbr, " +
		          "c.dept_nm " +
		"ORDER  BY total_trans_amt desc " + 
		"WITH UR";

	public static final int[] DEPARTMENT_NUMBERS = { 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 59 };
	public static final String[] DEPARTMENT_NAMES = { "LUMBER", "BUILDING MATERIALS", "FLOORING", "PAINT", "HARDWARE", "PLUMBING",
			"ELECTRICAL AND LIGHT", "SEASONAL/GARDEN", "KITCHEN AND BATH", "MILLWORK", "BLINDS AND WALLPAPER" };
}

package com.homedepot.mm.mr.validation;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class TransactionAttributesValidator {
	
	private static final Logger LOGGER = Logger.getLogger( TransactionAttributesValidator.class );

	private TransactionAttributesValidator() {}
	
	/**
	 * Used to validate the request attributes of the get top spend service
	 * @param customerId
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public Status validateSpendServiceRequest( String customerId, String fromDate, String toDate ) {
		Status status = Response.Status.OK;
		try {
			/** VALIDATING THE CUSTOMERID */
			if ( StringUtils.isEmpty( customerId ) ) {
				status = Response.Status.BAD_REQUEST;
			} else {
				try {
					Integer.parseInt( customerId );
				}
				catch ( NumberFormatException e ) {
					status = Response.Status.BAD_REQUEST;
				}
			}
			
			if ( LOGGER.isDebugEnabled() ) {
				LOGGER.debug( "From Date in Validator " + fromDate );
			}
			
			if ( StringUtils.isEmpty( fromDate ) ) {
				LOGGER.error( "Missing fromDate" );
				status = Response.Status.BAD_REQUEST;

			} else {
				if ( !fromDate.matches( "\\d{2}/\\d{2}/\\d{4}" ) ) {
					LOGGER.error( "Invalid fromDate format" );
					status = Response.Status.BAD_REQUEST;
				}
			}
			
			if( LOGGER.isDebugEnabled() ) {
				LOGGER.debug( "To Date in Validator " + toDate );
			}
			
			if ( StringUtils.isEmpty( toDate ) ) {
				LOGGER.error( "Missing toDate" );
				status = Response.Status.BAD_REQUEST;
			} else {
				if ( !toDate.matches( "\\d{2}/\\d{2}/\\d{4}" ) ) {
					LOGGER.error( "Invalid toDate format" );
					status = Response.Status.BAD_REQUEST;
				}
			}
		}
		catch ( Exception e ) {
			LOGGER.error( "Exception occured in validateSpendServiceRequest" + e.getMessage() );
			status = Response.Status.INTERNAL_SERVER_ERROR;
		}
		return status;
	}
}

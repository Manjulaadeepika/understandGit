package com.homedepot.mm.mr.service;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.homedepot.mm.mr.bl.impl.SpendManager;
import com.homedepot.mm.mr.dto.SpendResponse;
import com.homedepot.mm.mr.utility.Constants;
import com.homedepot.mm.mr.validation.TransactionAttributesValidator;

@Path( Constants.SPEND_SERVICE )
public class SpendService {

	private static final Logger LOGGER = Logger.getLogger( SpendService.class );

	@Autowired
	private SpendManager spendManager;
	
	@Autowired
	private TransactionAttributesValidator transactionAttributesValidator;

	@GET
	@Path( Constants.GET_TOP_SPEND_DEPT + "{ customerId }" )
	@Produces( MediaType.APPLICATION_JSON )
	public Response getTopSpend( @PathParam( Constants.PATH_PARAM_CUSTOMER_ID ) String customerId,
			@DefaultValue( Constants.ENGLISH_US ) @QueryParam( Constants.LANGUAGE_CODE ) String langCd,
												@QueryParam( Constants.QUERY_PARAM_START_DT ) String startDate,
												@QueryParam( Constants.QUERY_PARAM_END_DT ) String endDate
			) {
		long startTime = System.currentTimeMillis();
		long endTime = 0;
		Status status = Response.Status.OK;
		SpendResponse spendResponse = null;
		
		if ( LOGGER.isDebugEnabled() ) {
			LOGGER.debug( "Entering getTopSpendByDept -- SpendService" );
		}

		try {
			if ( LOGGER.isDebugEnabled() ) {
				LOGGER.debug( "customerId  : " + customerId );
				LOGGER.debug( "startDate: " + startDate );
				LOGGER.debug( "endDate: " + endDate );
			}
			
			status = transactionAttributesValidator.validateSpendServiceRequest( customerId, startDate, endDate );
			
			if ( status.equals( Response.Status.OK ) ) {
				spendResponse = spendManager.getSpendByDept( customerId, startDate, endDate, langCd );
			}
				
		} catch ( Exception exception ) {
			status = Response.Status.INTERNAL_SERVER_ERROR;
			LOGGER.fatal( exception );
		}

		if ( LOGGER.isDebugEnabled() ) {
			LOGGER.debug( "Exiting getTopSpendByDept -- SpendService -- Elapsed Time: "
					+ ( endTime - startTime ) + "ms" );
		}
		return Response
			.status( status )
			.entity( spendResponse )
			.build();
	}
}

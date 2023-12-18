package com.vinculum.ondc.event;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.vinculum.ondc.constants.Constants;
import com.vinculum.ondc.objects.request.Params;
import com.vinculum.ondc.objects.request.cancel.Cancel;
import com.vinculum.ondc.objects.request.confirm.Confirm;
import com.vinculum.ondc.objects.request.init.Init;
import com.vinculum.ondc.objects.request.search.Search;
import com.vinculum.ondc.objects.request.select.Select;
import com.vinculum.ondc.objects.response.ResponseStatus;
import com.vinculum.ondc.service.OndcService;

/**
 * The OndcEvent class represents the RESTful web service endpoint for handling ONDC events.
 * It provides methods for creating indices, retrieving data, performing searches and selections,
 * initializing ONDC, and performing health checks.
 * <p>
 * The class is annotated with the JAX-RS annotations to define the endpoint and HTTP methods for each operation.
 * It also uses the SLF4J logger for logging purposes.
 * </p>
 *
 * @since 1.0
 */

@Path("ondc")
public class OndcEvent {

    private OndcService service;
    
    Optional<Timestamp> requestTimestamp = Optional.empty();
    
    Optional<Timestamp> responseTimestamp = Optional.empty();

    public static final Logger LOGGER = LoggerFactory.getLogger(OndcEvent.class);

    /**
     * Creates an index with the provided data and headers.
     *
     * @param data    the data to create the index
     * @param headers the HTTP headers of the request
     * @return the response status
     * @throws Exception if an error occurs during the process
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("indice")
    public ResponseStatus createIndice(final String data, @Context HttpHeaders headers, @QueryParam("indexType") String indexType) throws Exception {
        // Call and Return
        return process(data, headers, Constants.H_I_CREATE, Constants.CREATE_INDEX_RESPONSE, Constants.REQ_TYPE_CREATE_INDEX+indexType);
    }

    /**
     * Retrieves data based on the provided parameters and headers.
     *
     * @param params  the parameters for retrieving data
     * @param headers the HTTP headers of the request
     * @return the response status
     * @throws Exception if an error occurs during the process
     */
    @GET
    @Path("data")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseStatus get(final Params params, @Context HttpHeaders headers) throws Exception {
        // Call and Return
        return process(params, headers, Constants.H_CREATE, Constants.GET_SKU_RESPONSE, Constants.REQ_TYPE_GET_SKUS);
    }

    /**
     * Performs a search operation based on the provided search criteria and headers.
     *
     * @param search  the search criteria
     * @param headers the HTTP headers of the request
     * @return the response status
     * @throws Exception if an error occurs during the process
     */
    @POST
    @Path("search")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseStatus search(Search search, @Context HttpHeaders headers) throws Exception {
        // Call and Return
        return process(search, headers, Constants.H_CREATE, Constants.SEARCH, Constants.REQ_TYPE_SEARCH);
    }

    /**
     * Performs a selection operation based on the provided selection criteria and headers.
     *
     * @param select  the selection criteria
     * @param headers the HTTP headers of the request
     * @return the response status
     * @throws Exception if an error occurs during the process
     */
    @POST
    @Path("select")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseStatus select(Select select, @Context HttpHeaders headers) throws Exception {
        // Call and Return
        return process(select, headers, Constants.H_CREATE, Constants.SELECT, Constants.REQ_TYPE_SELECT);
    }
    /**
     *  Performs init operations based on the provided init data, headers and criteria
     * @param init
     * @param headers the HTTP headers of the request
     * @return the response statusx
     * @throws Exception if occurs during the process
     */
	@POST	
	@Path("init")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseStatus init(Init init,@Context HttpHeaders headers) throws Exception {
		
		//Call and Return
		return process(init, headers, Constants.H_CREATE, Constants.INIT,Constants.REQ_TYPE_INIT);
	}
	
	/**
     *  Performs init operations based on the provided init data, headers and criteria
     * @param init
     * @param headers the HTTP headers of the request
     * @return the response statusx
     * @throws Exception if occurs during the process
     */
	@POST	
	@Path("confirm")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseStatus confirm(Confirm confirm,@Context HttpHeaders headers) throws Exception {
		
		//Call and Return
		requestTimestamp = Optional.of(new Timestamp(System.currentTimeMillis()));
		return process(confirm, headers, Constants.H_CREATE, Constants.INIT,Constants.REQ_TYPE_CONFIRM);
	}
	
	@POST	
	@Path("cancel")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseStatus cancel(Cancel cancel,@Context HttpHeaders headers) throws Exception {
		
		//Call and Return
		return process(cancel, headers, Constants.H_CREATE, Constants.CANCEL,Constants.REQ_TYPE_CANCEL);
	}

	


	private ResponseStatus process(final Object request, final HttpHeaders headers, final String headerstring, final String logstring, final String route) throws Exception {
		//Constant DbId for Now
		String dbId="50";
		//Set the MDC Context
		MDC.put(Constants.UNIQUE, dbId + Constants.F_SLASH + Constants.EVENTS + Constants.F_SLASH +  route);
		//Print
		print(headers, headerstring, request);
		//Process
		//ResponseStatus status = null;
		
		 CompletableFuture.runAsync(() -> {
		 //Assign 
		  //ResponseStatus status;
		 try 
		 {
			 /// status = service.process(request,route, dbId);
			service.process(request,route, dbId);  
			//Print
			//print(status, logstring);
			//Remove from the unique constants from Multiple document context 
			MDC.remove(Constants.UNIQUE);
			System.out.println("Async run");
			
			   //return status;
		 } catch (BadRequestException e) {
		        LOGGER.error("Bad request error during async process", e);
		        // handle the bad request
		    } catch (Exception e) {
		        LOGGER.error("Error during async process", e);
		        // handle the exception
		    }

		 });
		
		 ResponseStatus status = new ResponseStatus();
		 
		 if(isValidRequest(request))
		 {
		  status.setResponseMessage(Constants.ACKNOWLEDGE_SUCCESS);
		  requestTimestamp = Optional.of(new Timestamp(System.currentTimeMillis()));
		 }
		 else
		 {
		   status.setResponseMessage(Constants.ACKNOWLEDGE_FALIURE);
		   requestTimestamp = Optional.of(new Timestamp(System.currentTimeMillis()));
		 }
		 print(status, logstring);
		
		 if (requestTimestamp.isPresent() && responseTimestamp.isPresent()) {
			 
			    Timestamp requestTimestampValue = requestTimestamp.get();
			    Timestamp responseTimestampValue = responseTimestamp.get();

			    long timeDifference = responseTimestampValue.getTime() - requestTimestampValue.getTime();
			    System.out.println(" Time difference: " + timeDifference + " milliseconds");
			    
			} else {
			    System.out.println("Unable to determine time difference: One or both timestamps are missing.");
			}
		
	     return status;
	}


	private void print(final HttpHeaders headers, final String headerstring, final Object body) {
		//Null Check
		if(null != headers) {
			//Get All Headers
			final MultivaluedMap<String, String> map = headers.getRequestHeaders();
			//Check Null And Empty
			if(null != map) {
				//Get The Set
				final Set<Entry<String,List<String>>> values = map.entrySet();
				//Check Null And Empty
				if(null != values) {
					//Log The Information
					LOGGER.info(Constants.CROSS);
					LOGGER.info(headerstring);
					LOGGER.info(Constants.CROSS);
					//Loop
					for(final Entry<String,List<String>> value: values) {
						//Null check
						if(null != value) {
							//Get The Key
							final String key = value.getKey();
							//Get The Value
							final List<String> list = value.getValue();
							//log The Headers
							LOGGER.info(String.format(Constants.KEY_VAL, key, list));
						}
					}
					//Handle Exception
					try {
						//Print The body of the request
						LOGGER.info(String.format(Constants.REQUEST, service.getMetaData().getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(body)));
					}catch(final Exception exception) {
						//Log
						LOGGER.error(exception.getMessage(), exception.fillInStackTrace());
					}
					LOGGER.info(Constants.CROSS);
				}
			}
		}
	}

	private void print(final Object data, final String message) {
		//Handle Exception
		try {
			//Cast
			final String log = service.getMetaData().getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(data);
			//Print
			LOGGER.info(message + log);
		}catch(final Exception exception) {
			//Log
			LOGGER.error(exception.getMessage(),exception.fillInStackTrace());
		}
	}

	@GET
	@Path("healthcheck")
	public Response test() {
		//Return
		return Response.status(Response.Status.OK).entity(Constants.OK).build();
	}	

/**
 * 
 * @param service set the OndcSearcvice Object
 */
	public void setService(OndcService service) {
		this.service = service;
	}
	
	private boolean isValidRequest(final Object request) {  
		
	    if (request == null) {
	        return false;
	    }
	    else
	    {
	    	return true;
	    }
	}
}


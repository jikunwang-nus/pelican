package com.charsmart.data.api.layer;

import com.charsmart.data.common.Result;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * @Author: Wonder
 * @Date: Created on 2022/6/6 5:37 PM
 */
@Path("/order")
public interface OrderService {
    @PUT
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    Result createOrder(@QueryParam("itemCode") String itemCode, @QueryParam("count") Integer count);

    @PUT
    @Path("/cancel")
    @Produces(MediaType.APPLICATION_JSON)
    Result cancelOrder(@QueryParam("orderNum") String orderNum);
}

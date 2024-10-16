package com.axiell.arena.liferay.modules.arena.resource;

import com.axiell.arena.liferay.modules.arena.model.PaymentRequest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public interface ILocalDebtsResource {

    @POST
    @Path("/patrons/debts/agencymembers/{agencyMemberId}")
    void addPayment(@PathParam("agencyMemberId") Long agencyMemberId, PaymentRequest request);
}

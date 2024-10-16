package com.axiell.arena.liferay.modules.arena.resource;

import com.axiell.authinfo.AuthInfo;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public interface ILocalPatronsResource {

    @GET
    @Path("/patrons/agreements/unsigned")
    @Produces("*/*")
    Response getUnsignedAgreement(
            @Context HttpHeaders httpHeaders,
            @HeaderParam(HttpHeaders.AUTHORIZATION) AuthInfo authInfo);

    @POST
    @Path("/patrons/agreements/signed")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    Response saveSignedAgreement(@HeaderParam(HttpHeaders.AUTHORIZATION) AuthInfo authInfo, @QueryParam("mimeType") String mimeType,  @QueryParam("socialSecurityNo") String socialSecurityNo, byte[] bytes);
}

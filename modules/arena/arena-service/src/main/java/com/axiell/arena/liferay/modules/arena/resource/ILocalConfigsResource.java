package com.axiell.arena.liferay.modules.arena.resource;


import com.axiell.arena.liferay.modules.arena.model.dto.PortalSiteDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.jar.Attributes;

@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public interface ILocalConfigsResource {




    @GET
    @Path("/configs/portalsites")
    PortalSiteDTO getPortalSite(
            @QueryParam("vhost") String vhost,
            @QueryParam("friendlyUrl") String friendlyUrl);

    @GET
    @Path("/configs/portalsites/{portalSiteId}")
    PortalSiteDTO getPortalSite(
            @PathParam("portalSiteId") long portalSiteId);

    @GET
    @Path("/configs/about")
    Attributes about();
}

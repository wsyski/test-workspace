package com.axiell.arena.liferay.modules.arena.resource;

import com.axiell.arena.liferay.modules.arena.model.dto.AgencyMemberDTO;
import com.axiell.arena.liferay.modules.arena.model.dto.GroupDTO;
import com.axiell.arena.liferay.modules.arena.model.dto.PortalSiteDTO;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.jar.Attributes;

@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public interface ICentralConfigsResource {
    @GET
    @Path("/configs/about")
    Attributes about();


    @GET
    @Path("/configs/portalsites")
    PortalSiteDTO getPortalSite(
            @QueryParam("vhost") String vhost, @QueryParam("friendlyUrl") String friendlyUrl);

    @GET
    @Path("/configs/portalsites/{portalSiteId}")
    PortalSiteDTO getPortalSite(
            @PathParam("portalSiteId") long portalSiteId);

    @GET
    @Path("/configs/agencymembers/{agencyMemberId}")
    AgencyMemberDTO getAgencyMember(
            @PathParam("agencyMemberId") long agencyMemberId);

    @GET
    @Path("/configs/installations/{installationId}/groups/{groupPath}")
    GroupDTO getInstallationGroup(
            @PathParam("installationId") long installationId,
            @PathParam("groupPath") String groupPath);

    @GET
    @Path("/configs/agencies/{agencyId}/groups/{groupPath}")
    GroupDTO getAgencyGroup(
            @PathParam("agencyId") long agencyId,
            @PathParam("groupPath") String groupPath);


    @GET
    @Path("/configs/agencymembers/{agencyMemberId}/groups/{groupPath}")
    GroupDTO getAgencyMemberGroup(
            @PathParam("agencyMemberId") long agencyMemberId,
            @PathParam("groupPath") String groupPath);

    @GET
    @Path("/configs/portalsites/{portalSiteId}/groups/{groupPath}")
    GroupDTO getPortalSiteGroup(
            @PathParam("portalSiteId") long portalSiteId,
            @PathParam("groupPath") String groupPath);
}

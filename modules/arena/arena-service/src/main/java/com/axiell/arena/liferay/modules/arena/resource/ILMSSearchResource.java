package com.axiell.arena.liferay.modules.arena.resource;

import com.axiell.arena.liferay.modules.arena.model.dto.LMSSearchResultResponseDTO;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public interface ILMSSearchResource {

    @GET
    @Path("/lmssearch/agencymembers/{agencyMemberId}/search")
    LMSSearchResultResponseDTO search(@PathParam("agencyMemberId") Long arenaMemberId,
                                      @QueryParam("query") String query,
                                      @QueryParam("searchType") String searchType,
                                      @QueryParam("page") @DefaultValue("0") int page,
                                      @QueryParam("size") @DefaultValue("10") int size
    );
}

package com.axiell.arena.liferay.modules.arena.service.impl;

import com.axiell.arena.liferay.modules.arena.error.RestApiError;
import com.axiell.arena.liferay.modules.arena.exception.RestApiException;
import com.axiell.arena.liferay.modules.arena.model.AgencyMember;
import com.axiell.arena.liferay.modules.arena.model.Group;
import com.axiell.arena.liferay.modules.arena.model.PortalSite;
import com.axiell.arena.liferay.modules.arena.model.dto.AgencyMemberDTO;
import com.axiell.arena.liferay.modules.arena.model.dto.AgencyMemberDTOModelMapper;
import com.axiell.arena.liferay.modules.arena.model.dto.GroupDTO;
import com.axiell.arena.liferay.modules.arena.model.dto.GroupDTOModelMapper;
import com.axiell.arena.liferay.modules.arena.model.dto.PortalSiteDTO;
import com.axiell.arena.liferay.modules.arena.model.dto.PortalSiteDTOModelMapper;
import com.axiell.arena.liferay.modules.arena.resource.ArenaCentralResourceFactory;
import com.axiell.arena.liferay.modules.arena.resource.ICentralConfigsResource;
import com.axiell.arena.liferay.modules.arena.service.base.ArenaCentralLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import org.osgi.service.component.annotations.Component;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.jar.Attributes;

import static com.axiell.arena.liferay.modules.arena.util.EncodingUtil.encodePath;

/**
 * The implementation of the federated search local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.axiell.arena.liferay.modules.federated_search.service.FederatedSearchLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ArenaCentralLocalServiceBaseImpl
 */
@Component(
        property = "model.class.name=com.axiell.arena.liferay.modules.arena.model.ArenaCentral",
        service = AopService.class
)
public class ArenaCentralLocalServiceImpl
        extends ArenaCentralLocalServiceBaseImpl {

    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never reference this class directly. Use <code>com.axiell.arena.liferay.modules.arena.service.ArenaCentralLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.axiell.arena.liferay.modules.arena.service.ArenaCentralLocalServiceUtil</code>.
     */

    private static final Log _log = LogFactoryUtil.getLog(ArenaCentralLocalServiceImpl.class);

    @Override
    public PortalSite getPortalSite(String vhost, String friendlyUrl) {
        ICentralConfigsResource configsResource = ArenaCentralResourceFactory.INSTANCE.createResource(ICentralConfigsResource.class);
        PortalSiteDTO portalSiteDTO = configsResource.getPortalSite(vhost, friendlyUrl);
        return PortalSiteDTOModelMapper.INSTANCE.toModel(portalSiteDTO);
    }

    @Override
    public AgencyMember getAgencyMember(long id) {
        ICentralConfigsResource configsResource = ArenaCentralResourceFactory.INSTANCE.createResource(ICentralConfigsResource.class);
        AgencyMemberDTO agencyMemberDTO = configsResource.getAgencyMember(id);
        return AgencyMemberDTOModelMapper.INSTANCE.toModel(agencyMemberDTO);
    }

    @Override
    public PortalSite getPortalSite(long id) {
        ICentralConfigsResource configsResource = ArenaCentralResourceFactory.INSTANCE.createResource(ICentralConfigsResource.class);
        PortalSiteDTO portalSiteDTO = configsResource.getPortalSite(id);
        return PortalSiteDTOModelMapper.INSTANCE.toModel(portalSiteDTO);
    }

    @Override
    public Attributes about() {
        ICentralConfigsResource configsResource = ArenaCentralResourceFactory.INSTANCE.createResource(ICentralConfigsResource.class);
        return configsResource.about();
    }

    @Override
    public Group getInstallationGroup(long installationId, String groupPath) throws RestApiException {
        try {
            ICentralConfigsResource configsResource = ArenaCentralResourceFactory.INSTANCE.createResource(ICentralConfigsResource.class);
            GroupDTO groupDTO = configsResource.getInstallationGroup(installationId, encodePath(groupPath));
            return GroupDTOModelMapper.INSTANCE.toModel(groupDTO);
        } catch (WebApplicationException ex) {
            Response response = ex.getResponse();
            checkResponse(response);
            throw ex;
        }
    }

    @Override
    public Group getAgencyGroup(long agencyId, String groupPath) throws RestApiException {
        try {
            ICentralConfigsResource configsResource = ArenaCentralResourceFactory.INSTANCE.createResource(ICentralConfigsResource.class);
            GroupDTO groupDTO = configsResource.getAgencyGroup(agencyId, encodePath(groupPath));
            return GroupDTOModelMapper.INSTANCE.toModel(groupDTO);
        } catch (WebApplicationException ex) {
            Response response = ex.getResponse();
            checkResponse(response);
            throw ex;
        }
    }

    @Override
    public Group getAgencyMemberGroup(long agencyMemberId, String groupPath) {
        ICentralConfigsResource configsResource = ArenaCentralResourceFactory.INSTANCE.createResource(ICentralConfigsResource.class);
        GroupDTO groupDTO = configsResource.getAgencyMemberGroup(agencyMemberId, encodePath(groupPath));
        return GroupDTOModelMapper.INSTANCE.toModel(groupDTO);
    }

    @Override
    public Group getPortalSiteGroup(long portalSiteId, String groupPath) throws RestApiException {
        try {
            ICentralConfigsResource configsResource = ArenaCentralResourceFactory.INSTANCE.createResource(ICentralConfigsResource.class);
            GroupDTO groupDTO = configsResource.getPortalSiteGroup(portalSiteId, encodePath(groupPath));
            return GroupDTOModelMapper.INSTANCE.toModel(groupDTO);
        } catch (WebApplicationException ex) {
            Response response = ex.getResponse();
            checkResponse(response);
            throw ex;
        }
    }

    private void checkResponse(final Response response) throws RestApiException {
        if (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode() || response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            RestApiError restApiError = response.readEntity(RestApiError.class);
            RestApiException restApiException = new RestApiException(restApiError);
            _log.error(restApiError, restApiException);
            throw restApiException;
        }
    }
}

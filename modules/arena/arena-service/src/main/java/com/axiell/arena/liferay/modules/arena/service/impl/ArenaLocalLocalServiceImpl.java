package com.axiell.arena.liferay.modules.arena.service.impl;

import com.axiell.arena.liferay.modules.arena.constants.ArenaConstants;
import com.axiell.arena.liferay.modules.arena.error.RestApiError;
import com.axiell.arena.liferay.modules.arena.exception.RestApiException;
import com.axiell.arena.liferay.modules.arena.model.Agreement;
import com.axiell.arena.liferay.modules.arena.model.Group;
import com.axiell.arena.liferay.modules.arena.model.LMSSearchResultResponse;
import com.axiell.arena.liferay.modules.arena.model.PaymentRequest;
import com.axiell.arena.liferay.modules.arena.model.PortalSite;
import com.axiell.arena.liferay.modules.arena.model.dto.LMSSearchResultResponseDTO;
import com.axiell.arena.liferay.modules.arena.model.dto.LMSSearchResultResponseDTOModelMapper;
import com.axiell.arena.liferay.modules.arena.model.dto.PortalSiteDTO;
import com.axiell.arena.liferay.modules.arena.model.dto.PortalSiteDTOModelMapper;
import com.axiell.arena.liferay.modules.arena.resource.ArenaLocalResourceFactory;
import com.axiell.arena.liferay.modules.arena.resource.ILMSSearchResource;
import com.axiell.arena.liferay.modules.arena.resource.ILocalConfigsResource;
import com.axiell.arena.liferay.modules.arena.resource.ILocalDebtsResource;
import com.axiell.arena.liferay.modules.arena.resource.ILocalPatronsResource;
import com.axiell.arena.liferay.modules.arena.service.base.ArenaLocalLocalServiceBaseImpl;
import com.axiell.authinfo.AuthInfo;
import com.axiell.authinfo.ConstantAuthHeaderSecretKeyResolver;
import com.axiell.authinfo.IAuthHeaderSecretKeyResolver;
import com.axiell.authinfo.Patron;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Locale;
import java.util.jar.Attributes;

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
 * @see ArenaLocalLocalServiceBaseImpl
 */
@Component(
        property = "model.class.name=com.axiell.arena.liferay.modules.arena.model.ArenaLocal",
        service = AopService.class
)
public class ArenaLocalLocalServiceImpl extends ArenaLocalLocalServiceBaseImpl {
    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never reference this class directly. Always use {@link com.axiell.arena.liferay.modules.arena.service.ArenaLocalLocalServiceUtil} to access the arena local local service.
     */

    @Override
    public PortalSite getPortalSite(String vhost, String friendlyUrl) {
        ILocalConfigsResource configsResource = ArenaLocalResourceFactory.INSTANCE.createResource(ILocalConfigsResource.class);
        PortalSiteDTO portalSiteDTO = configsResource.getPortalSite(vhost, friendlyUrl);
        return PortalSiteDTOModelMapper.INSTANCE.toModel(portalSiteDTO);
    }

    @Override
    public PortalSite getPortalSite(long id) {
        ILocalConfigsResource configsResource = ArenaLocalResourceFactory.INSTANCE.createResource(ILocalConfigsResource.class);
        PortalSiteDTO portalSiteDTO = configsResource.getPortalSite(id);
        return PortalSiteDTOModelMapper.INSTANCE.toModel(portalSiteDTO);
    }

    @Override
    public void addPayment(Long agencyMemberId, PaymentRequest request) throws RestApiException {
        try {
            ILocalDebtsResource localDebtsResource = ArenaLocalResourceFactory.INSTANCE.createResource(ILocalDebtsResource.class);
            localDebtsResource.addPayment(agencyMemberId, request);
        } catch (WebApplicationException ex) {
            Response response = ex.getResponse();
            checkResponse(response);
            throw ex;
        }
    }

    @Override
    public Agreement getUnsignedAgreement(final String patronId, final long agencyMemberId, final Locale locale) throws RestApiException {
        IAuthHeaderSecretKeyResolver authHeaderSecretKeyResolver = getAuthHeaderSecretKeyResolver(agencyMemberId);
        ArenaLocalResourceFactory arenaLocalResourceFactory = new ArenaLocalResourceFactory(authHeaderSecretKeyResolver);
        ILocalPatronsResource patronsResource = arenaLocalResourceFactory.createResource(ILocalPatronsResource.class, locale);
        AuthInfo authInfo = getAuthInfo(patronId, agencyMemberId);
        Response response = patronsResource.getUnsignedAgreement(null, authInfo);
        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            return null;
        } else {
            checkResponse(response);
            byte[] bytes = response.readEntity(byte[].class);
            MediaType mediaType = response.getMediaType();
            return new Agreement(mediaType.toString(), bytes);
        }
    }

    @Override
    public void saveSignedAgreement(final String patronId, final long agencyMemberId, final Agreement agreement, final String socialSecurityNo, final Locale locale) throws RestApiException {
        IAuthHeaderSecretKeyResolver authHeaderSecretKeyResolver = getAuthHeaderSecretKeyResolver(agencyMemberId);
        ArenaLocalResourceFactory arenaLocalResourceFactory = new ArenaLocalResourceFactory(authHeaderSecretKeyResolver);
        ILocalPatronsResource patronsResource = arenaLocalResourceFactory.createResource(ILocalPatronsResource.class, locale);
        AuthInfo authInfo = getAuthInfo(patronId, agencyMemberId);
        Response response = patronsResource.saveSignedAgreement(authInfo, agreement.getMimeType(), socialSecurityNo, agreement.getContent());
        checkResponse(response);
    }

    @Override
    public LMSSearchResultResponse getLMSSearch(final long agencyMemberId, final String query, final String type, final int start, final int size) throws RestApiException {
        ILMSSearchResource lmsSearchResource = ArenaLocalResourceFactory.INSTANCE.createResource(ILMSSearchResource.class);

        LMSSearchResultResponseDTO searchResults = lmsSearchResource.search(agencyMemberId, query, type, start, size);

        return LMSSearchResultResponseDTOModelMapper.INSTANCE.toModel(searchResults);
    }

    @Override
    public Attributes about() {
        ILocalConfigsResource configsResource = ArenaLocalResourceFactory.INSTANCE.createResource(ILocalConfigsResource.class);
        return configsResource.about();
    }

    private void checkResponse(final Response response) throws RestApiException {
        if (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
            RestApiError restApiError = response.readEntity(RestApiError.class);
            RestApiException restApiException = new RestApiException(restApiError);
            _log.error(restApiError, restApiException);
            throw restApiException;
        }
    }

    private IAuthHeaderSecretKeyResolver getAuthHeaderSecretKeyResolver(final long agencyMemberId) {
        Group restApiGroup = arenaCentralLocalService.getAgencyMemberGroup(agencyMemberId, ArenaConstants.GROUP_NAME_REST_API);
        ConstantAuthHeaderSecretKeyResolver authHeaderSecretKeyResolver = new ConstantAuthHeaderSecretKeyResolver();
        String secretKey = restApiGroup.getPropertyAsString(ArenaConstants.PROP_NAME_REST_API_SECRET_KEY);
        long expirationTimeInSeconds = restApiGroup.getPropertyAsLong(ArenaConstants.PROP_NAME_TOKEN_EXPIRATION_TIME_IN_SECONDS);
        long leewayInSeconds = restApiGroup.getPropertyAsLong(ArenaConstants.PROP_NAME_TOKEN_LEEWAY_IN_SECONDS);
        authHeaderSecretKeyResolver.setSecretKey(secretKey);
        authHeaderSecretKeyResolver.setExpirationTimeInSeconds(expirationTimeInSeconds);
        authHeaderSecretKeyResolver.setLeewayInSeconds(leewayInSeconds);
        return authHeaderSecretKeyResolver;
    }

    private AuthInfo getAuthInfo(final String patronId, final long agencyMemberId) {
        Patron.Builder patronBuilder = new Patron.Builder().id(patronId);
        return new AuthInfo.Builder().arenaAgencyMemberId(agencyMemberId).patron(patronBuilder.build()).build();
    }

    @Reference
    protected com.axiell.arena.liferay.modules.arena.service.ArenaCentralLocalService arenaCentralLocalService;

    private static final Log _log = LogFactoryUtil.getLog(ArenaLocalLocalServiceImpl.class);
}

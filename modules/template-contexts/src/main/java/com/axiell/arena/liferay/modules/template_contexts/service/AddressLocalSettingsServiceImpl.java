package com.axiell.arena.liferay.modules.template_contexts.service;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.*;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component(service = AddressLocalSettingsService.class)
public class AddressLocalSettingsServiceImpl implements AddressLocalSettingsService {

    private AddressLocalService addressLocalService;

    @Override
    public String getGoogleCalendarApiKey() {
        return getAddress().getStreet1();
    }

    @Override
    public String getGoogleCalendarId() {
        return getAddress().getCity();
    }

    Address getAddress() {
        List<Address> addresses = addressLocalService.getAddresses();
        return addresses.stream().findFirst().orElse(new Address() {
            @Override
            public Country getCountry() {
                return null;
            }

            @Override
            public String getPhoneNumber() {
                return null;
            }

            @Override
            public Region getRegion() {
                return null;
            }

            @Override
            public ListType getListType() {
                return null;
            }

            @Override
            public long getPrimaryKey() {
                return 0;
            }

            @Override
            public void setPrimaryKey(long primaryKey) {

            }

            @Override
            public long getMvccVersion() {
                return 0;
            }

            @Override
            public void setMvccVersion(long mvccVersion) {

            }

            @Override
            public long getCtCollectionId() {
                return 0;
            }

            @Override
            public void setCtCollectionId(long l) {

            }

            @Override
            public String getUuid() {
                return "";
            }

            @Override
            public void setUuid(String uuid) {

            }

            @Override
            public String getExternalReferenceCode() {
                return null;
            }

            @Override
            public void setExternalReferenceCode(String s) {

            }

            @Override
            public long getAddressId() {
                return 0;
            }

            @Override
            public void setAddressId(long addressId) {

            }

            @Override
            public long getCompanyId() {
                return 0;
            }

            @Override
            public void setCompanyId(long companyId) {

            }

            @Override
            public long getUserId() {
                return 0;
            }

            @Override
            public void setUserId(long userId) {

            }

            @Override
            public String getUserUuid() {
                return "";
            }

            @Override
            public void setUserUuid(String userUuid) {

            }

            @Override
            public String getUserName() {
                return "";
            }

            @Override
            public void setUserName(String userName) {

            }

            @Override
            public Date getCreateDate() {
                return null;
            }

            @Override
            public void setCreateDate(Date createDate) {

            }

            @Override
            public Date getModifiedDate() {
                return null;
            }

            @Override
            public void setModifiedDate(Date modifiedDate) {

            }

            @Override
            public String getClassName() {
                return "";
            }

            @Override
            public void setClassName(String className) {

            }

            @Override
            public long getClassNameId() {
                return 0;
            }

            @Override
            public void setClassNameId(long classNameId) {

            }

            @Override
            public long getClassPK() {
                return 0;
            }

            @Override
            public void setClassPK(long classPK) {

            }

            @Override
            public String getStreet1() {
                return "";
            }

            @Override
            public void setStreet1(String street1) {

            }

            @Override
            public String getStreet2() {
                return "";
            }

            @Override
            public void setStreet2(String street2) {

            }

            @Override
            public String getStreet3() {
                return "";
            }

            @Override
            public void setStreet3(String street3) {

            }

            @Override
            public Date getValidationDate() {
                return null;
            }

            @Override
            public void setValidationDate(Date date) {

            }

            @Override
            public int getValidationStatus() {
                return 0;
            }

            @Override
            public void setValidationStatus(int i) {

            }

            @Override
            public String getCity() {
                return "";
            }

            @Override
            public void setCity(String city) {

            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public void setDescription(String s) {

            }

            @Override
            public double getLatitude() {
                return 0;
            }

            @Override
            public void setLatitude(double v) {

            }

            @Override
            public double getLongitude() {
                return 0;
            }

            @Override
            public void setLongitude(double v) {

            }

            @Override
            public String getZip() {
                return "";
            }

            @Override
            public void setZip(String zip) {

            }

            @Override
            public Address cloneWithOriginalValues() {
                return null;
            }

            @Override
            public long getRegionId() {
                return 0;
            }

            @Override
            public void setRegionId(long regionId) {

            }

            @Override
            public long getCountryId() {
                return 0;
            }

            @Override
            public void setCountryId(long countryId) {

            }

            @Override
            public long getListTypeId() {
                return 0;
            }

            @Override
            public void setListTypeId(long l) {

            }


            @Override
            public boolean getMailing() {
                return false;
            }

            @Override
            public boolean isMailing() {
                return false;
            }

            @Override
            public void setMailing(boolean mailing) {

            }

            @Override
            public String getName() {
                return null;
            }

            @Override
            public void setName(String s) {

            }

            @Override
            public boolean getPrimary() {
                return false;
            }

            @Override
            public boolean isPrimary() {
                return false;
            }

            @Override
            public void setPrimary(boolean primary) {

            }

            @Override
            public boolean isNew() {
                return false;
            }

            @Override
            public void setNew(boolean n) {

            }

            @Override
            public boolean isCachedModel() {
                return false;
            }

            @Override
            public void setCachedModel(boolean cachedModel) {

            }

            @Override
            public boolean isEscapedModel() {
                return false;
            }

            @Override
            public Serializable getPrimaryKeyObj() {
                return null;
            }

            @Override
            public void setPrimaryKeyObj(Serializable primaryKeyObj) {

            }

            @Override
            public ExpandoBridge getExpandoBridge() {
                return null;
            }

            @Override
            public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {

            }

            @Override
            public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {

            }

            @Override
            public void setExpandoBridgeAttributes(ServiceContext serviceContext) {

            }

            @Override
            public int compareTo(Address address) {
                return 0;
            }

            @Override
            public CacheModel<Address> toCacheModel() {
                return null;
            }

            @Override
            public Address toEscapedModel() {
                return null;
            }

            @Override
            public Address toUnescapedModel() {
                return null;
            }

            @Override
            public String toXmlString() {
                return "";
            }

            @Override
            public Map<String, Object> getModelAttributes() {
                return null;
            }

            @Override
            public boolean isEntityCacheEnabled() {
                return false;
            }

            @Override
            public boolean isFinderCacheEnabled() {
                return false;
            }

            @Override
            public void resetOriginalValues() {

            }

            @Override
            public void setModelAttributes(Map<String, Object> attributes) {

            }

            @Override
            public StagedModelType getStagedModelType() {
                return null;
            }

            @Override
            public Class<?> getModelClass() {
                return null;
            }

            @Override
            public String getModelClassName() {
                return "";
            }

            @Override
            public void persist() {

            }

            @Override
            public Object clone() {
                try {
                    return super.clone();
                } catch (CloneNotSupportedException e) {
                    return null;
                }
            }
        });
    }

    @Reference
    protected void setAddressLocalService(AddressLocalService addressLocalService) {
        this.addressLocalService = addressLocalService;
    }

}

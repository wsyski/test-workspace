package com.axiell.arena.liferay.modules.template_contexts.model;

import com.axiell.arena.liferay.modules.template_contexts.service.AddressLocalSettingsService;

public class LibraryBranchModel extends AbstractImageModel {

    private AddressLocalSettingsService addressLocalSettingsService;
    private String libraryName;
    private String branchImageText;
    private String twitterLink;
    private String instaLink;
    private String fbLink;
    private String email;
    private String phone;
    private String zip;
    private String address;

    public String getLibraryName() {
        return libraryName;
    }

    void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public boolean hasBranchImageText() {
        return branchImageText != null && !branchImageText.isEmpty();
    }

    public String getBranchImageText() {
        return branchImageText;
    }

    void setBranchImageText(String branchImageText) {
        this.branchImageText = branchImageText;
    }

    public String getTwitterLink() {
        return twitterLink;
    }

    public boolean hasTwitterLink() {
        return twitterLink != null && !twitterLink.isEmpty();
    }

    void setTwitterLink(String twitterLink) {
        this.twitterLink = twitterLink;
    }

    public String getInstaLink() {
        return instaLink;
    }

    public boolean hasInstaLink() {
        return instaLink != null && !instaLink.isEmpty();
    }

    void setInstaLink(String instaLink) {
        this.instaLink = instaLink;
    }

    public String getFbLink() {
        return fbLink;
    }

    public boolean hasFbLink() {
        return fbLink != null && !fbLink.isEmpty();
    }

    void setFbLink(String fbLink) {
        this.fbLink = fbLink;
    }

    public String getEmail() {
        return email;
    }

    public boolean hasEmail() {
        return email != null && !email.isEmpty();
    }

    void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public boolean hasPhone() {
        return phone != null && !phone.isEmpty();
    }

    void setPhone(String phone) {
        this.phone = phone;
    }

    public String getZip() {
        return zip == null ? "" : zip;
    }

    public boolean hasZip() {
        return zip != null && !zip.isEmpty();
    }

    void setZip(String zip) {
        this.zip = zip;
    }

    public String getAddress() {
        return address == null ? "" : address;
    }

    public boolean hasAddress() {
        return address != null && !address.isEmpty();
    }

    void setAddress(String address) {
        this.address = address;
    }

    public String getApiKey() {
        return addressLocalSettingsService.getGoogleCalendarApiKey();
    }

    public String getCalendarId() {
        return addressLocalSettingsService.getGoogleCalendarId();
    }

    void setAddressLocalSettingsService(AddressLocalSettingsService addressLocalSettingsService) {
        this.addressLocalSettingsService = addressLocalSettingsService;
    }
}

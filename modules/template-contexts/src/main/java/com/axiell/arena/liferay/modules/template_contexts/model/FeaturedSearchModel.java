package com.axiell.arena.liferay.modules.template_contexts.model;

import com.axiell.arena.liferay.modules.template_contexts.service.CatalogueReferenceService;
import com.liferay.portal.kernel.exception.PortalException;

public class FeaturedSearchModel extends AbstractImageModel {
    private String title;
    private String query;
    private CatalogueReferenceService catalogueReferenceService;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getSearchUrl(String searchPath, long groupId) throws PortalException {
        return catalogueReferenceService.getSearchUrl(searchPath, query, groupId);
    }

    void setCatalogueReferenceService(CatalogueReferenceService catalogueReferenceService) {
        this.catalogueReferenceService = catalogueReferenceService;
    }
}

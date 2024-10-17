package com.axiell.arena.liferay.modules.template_contexts.model;


import com.axiell.arena.liferay.modules.template_contexts.service.CatalogueReferenceService;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DynamicListModel extends AbstractEditableModel {

    private String title;
    private String query;
    private int limit;
    private boolean showMediaclass = false;
    private boolean showExtent = false;
    private CatalogueReferenceService catalogueReferenceService;
    private String sortField;
    private String sortDirection;

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

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public boolean isShowMediaclass() {
        return showMediaclass;
    }

    public void setShowMediaclass(boolean showMediaclass) {
        this.showMediaclass = showMediaclass;
    }

    public boolean isShowExtent() {
        return showExtent;
    }

    public void setShowExtent(boolean showExtent) {
        this.showExtent = showExtent;
    }

    public String getSearchUrl(String searchPath, long groupId) throws PortalException {
        return catalogueReferenceService.getSearchUrl(searchPath, query, groupId);
    }

    public List<RecordModel> getRecords(int limit, long groupId) throws IOException, ExecutionException {
        return catalogueReferenceService.getRecords(query, limit, groupId);
    }

    public List<RecordModel> getRecords(int limit, long groupId, String sortField, String sortDirection) throws IOException, ExecutionException {
        return catalogueReferenceService.getRecords(query, limit, groupId, sortField, sortDirection);
    }

    void setCatalogueReferenceService(CatalogueReferenceService catalogueReferenceService) {
        this.catalogueReferenceService = catalogueReferenceService;
    }

    public String getSortField(){
        return sortField;
    }

    public  String getSortDirection(){
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public void setSortField(String sortField){
        this.sortField = sortField;
    }
}

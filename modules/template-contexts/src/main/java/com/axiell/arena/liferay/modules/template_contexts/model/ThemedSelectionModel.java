package com.axiell.arena.liferay.modules.template_contexts.model;

import java.util.List;

public class ThemedSelectionModel extends AbstractImageModel {
    private String title;
    private String summary;
    private List<DynamicListModel> dynamicLists;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public boolean hasDynamicLists() {
        return dynamicLists != null && !dynamicLists.isEmpty();
    }

    public List<DynamicListModel> getDynamicLists() {
        return dynamicLists;
    }

    public void setDynamicLists(List<DynamicListModel> dynamicLists) {
        this.dynamicLists = dynamicLists;
    }
}

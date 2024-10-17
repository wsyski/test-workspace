package com.axiell.arena.liferay.modules.template_contexts.model;

public abstract class AbstractViewableModel extends AbstractEditableModel {
    private String viewUrl;

    public String getViewUrl() {
        return viewUrl;
    }

    public void setViewUrl(String viewUrl) {
        this.viewUrl = viewUrl;
    }
}

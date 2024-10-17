package com.axiell.arena.liferay.modules.template_contexts.model;

public abstract class AbstractEditableModel {

    private String editIconTitle;
    private String editUrl;

    public boolean isEditIconVisible() {
        return editIconTitle != null && editUrl != null;
    }

    public String getEditIconTitle() {
        return editIconTitle;
    }

    void setEditIconTitle(String editIconTitle) {
        this.editIconTitle = editIconTitle;
    }

    public String getEditUrl() {
        return editUrl;
    }

    void setEditUrl(String editUrl) {
        this.editUrl = editUrl;
    }
}

package com.axiell.arena.liferay.modules.template_contexts.service;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import java.util.Locale;

public interface EditIconService {

    class EditIconModel {
        private String editIconTitle;
        private String editUrl;

        public String getEditIconTitle() {
            return editIconTitle;
        }

        public void setEditIconTitle(String editIconTitle) {
            this.editIconTitle = editIconTitle;
        }

        public String getEditUrl() {
            return editUrl;
        }

        public void setEditUrl(String editUrl) {
            this.editUrl = editUrl;
        }
    }


    EditIconModel getEditIconModel(AssetEntry assetEntry, Locale locale,
                                   LiferayPortletRequest renderRequest, LiferayPortletResponse renderResponse)
            throws Exception;
}

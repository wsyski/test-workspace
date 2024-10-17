package com.axiell.arena.liferay.modules.template_contexts.service;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.journal.web.internal.asset.model.JournalArticleAssetRenderer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.WindowStateFactory;
import com.liferay.portal.kernel.util.HtmlUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import java.util.Locale;

@Component(
        immediate = true,
        service = EditIconService.class
)
public class EditIconServiceImpl implements EditIconService {

    private StaticContextService staticContextService;

    @Override
    public EditIconModel getEditIconModel(AssetEntry assetEntry, Locale locale, LiferayPortletRequest renderRequest, LiferayPortletResponse renderResponse) throws Exception {
        JournalArticleAssetRenderer renderer = (JournalArticleAssetRenderer) assetEntry.getAssetRenderer();
        if (renderer.hasEditPermission(staticContextService.getThemeDisplay().getPermissionChecker())) {
            PortletURL redirectURL = renderResponse.createRenderURL();
            redirectURL.setParameter("struts_action", "/asset_publisher/add_asset_redirect");
            redirectURL.setWindowState(new WindowState("pop_up"));

            PortletURL editPortletURL = renderer.getURLEdit(renderRequest, renderResponse,
                    WindowStateFactory.getWindowState("pop_up"), redirectURL);
            if (editPortletURL != null) {
                String title = LanguageUtil.format(locale, "edit-x", HtmlUtil.escape(renderer.getTitle(locale)));
                EditIconModel editIconModel = new EditIconModel();
                editIconModel.setEditUrl(String.format("javascript:Liferay.Util.openWindow({dialog: {width: 960}, id:'%seditAsset', title: '%s', uri:'%s'});",
                        renderResponse.getNamespace(), title, HtmlUtil.escapeURL(editPortletURL.toString())));
                editIconModel.setEditIconTitle(title);
                return editIconModel;
            }
        }
        return null;
    }

    @Reference
    public void setStaticContextService(StaticContextService staticContextService) {
        this.staticContextService = staticContextService;
    }
}

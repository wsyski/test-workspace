package com.axiell.arena.liferay.modules.template_contexts.model;

import com.axiell.arena.liferay.modules.template_contexts.api.LibraryBranchFactoryBuilderFactory;
import com.axiell.arena.liferay.modules.template_contexts.api.ModelFactory;
import com.axiell.arena.liferay.modules.template_contexts.api.ModelFactoryBuilder;
import com.axiell.arena.liferay.modules.template_contexts.service.AddressLocalSettingsService;
import com.axiell.arena.liferay.modules.template_contexts.service.EditIconService;
import com.axiell.arena.liferay.modules.template_contexts.service.StaticContextService;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.web.internal.asset.model.JournalArticleAssetRenderer;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Map;

@Component(
        immediate = true,
        service = LibraryBranchFactoryBuilderFactory.class
)
public class LibraryBranchFactoryBuilderFactoryImpl implements LibraryBranchFactoryBuilderFactory {

    private EditIconService editIconService;
    private AddressLocalSettingsService addressLocalSettingsService;
    private StaticContextService staticContextService;

    @Override
    public ModelFactoryBuilder assetFactory() {
        return new AbstractModelFactoryBuilder() {

            @Override
            protected ModelFactory getFactoryInstance() {
                LibraryBranchFactory libraryBranchFactory = new LibraryBranchFactory();
                libraryBranchFactory.setLocale(getLocale());
                libraryBranchFactory.setRenderRequest(getRenderRequest());
                libraryBranchFactory.setRenderResponse(getRenderResponse());
                libraryBranchFactory.setTemplateNodes(getTemplateNodes());
                return libraryBranchFactory;
            }
        };
    }

    @Override
    public String getModelName() {
        return "Branch Information";
    }

    class LibraryBranchFactory extends AbstractModelFactory {

        @Override
        @SuppressWarnings("unchecked")
        public LibraryBranchModel newInstance(Map<String, Object> dataModel) {
            LibraryBranchModel libraryBranchModel = new LibraryBranchModel();
            libraryBranchModel.setAddressLocalSettingsService(addressLocalSettingsService);
            libraryBranchModel.setAddress(getDataModelString((Map<String, Object>) dataModel.get("columnContact"), "adress"));
            libraryBranchModel.setImage(getDataModelString((Map<String, Object>) dataModel.get("columnExtra"), "branchImage"));
            libraryBranchModel.setAltText(getDataModelString((Map<String, Object>) dataModel.get("columnExtra"), "branchImageAltText"));
            libraryBranchModel.setBranchImageText(getDataModelString((Map<String, Object>) dataModel.get("columnExtra"), "branchImageText"));
            libraryBranchModel.setEmail(getDataModelString((Map<String, Object>) dataModel.get("columnContact"), "email"));
            libraryBranchModel.setFbLink(getDataModelString((Map<String, Object>) dataModel.get("columnContact"), "fbLink"));
            libraryBranchModel.setInstaLink(getDataModelString((Map<String, Object>) dataModel.get("columnContact"), "instaLink"));
            libraryBranchModel.setLibraryName(getDataModelString((Map<String, Object>) dataModel.get("municipalityName"), "libraryName"));
            libraryBranchModel.setPhone(getDataModelString((Map<String, Object>) dataModel.get("columnContact"), "phone"));
            libraryBranchModel.setTwitterLink(getDataModelString((Map<String, Object>) dataModel.get("columnContact"), "twitterLink"));
            libraryBranchModel.setZip(getDataModelString((Map<String, Object>) dataModel.get("columnContact"), "zip"));
            return libraryBranchModel;
        }

        @Override
        public LibraryBranchModel newInstance(AssetEntry assetEntry) throws Exception {
            JournalArticleAssetRenderer renderer = (JournalArticleAssetRenderer) assetEntry.getAssetRenderer();
            JournalArticle article = renderer.getArticle();
            Document docXml = SAXReaderUtil.read(article.getContentByLocale(getLocale().toString()));
            LibraryBranchModel libraryBranchModel = new LibraryBranchModel();
            injectEditIcon(editIconService, assetEntry, libraryBranchModel);
            injectViewUrl(staticContextService, assetEntry, libraryBranchModel);
            libraryBranchModel.setAddressLocalSettingsService(addressLocalSettingsService);
            libraryBranchModel.setAddress(getElementValue(docXml, "adress"));
            libraryBranchModel.setImage(getElementValue(docXml, "branchImage"));
            libraryBranchModel.setAltText(getElementValue(docXml, "branchImageAltText"));
            libraryBranchModel.setBranchImageText(getElementValue(docXml, "branchImageText"));
            libraryBranchModel.setEmail(getElementValue(docXml, "email"));
            libraryBranchModel.setFbLink(getElementValue(docXml, "fbLink"));
            libraryBranchModel.setInstaLink(getElementValue(docXml, "instaLink"));
            libraryBranchModel.setLibraryName(getElementValue(docXml, "libraryName"));
            libraryBranchModel.setPhone(getElementValue(docXml, "phone"));
            libraryBranchModel.setTwitterLink(getElementValue(docXml, "twitterLink"));
            libraryBranchModel.setZip(getElementValue(docXml, "zip"));
            return libraryBranchModel;
        }
    }

    @Reference
    protected void setEditIconService(EditIconService editIconService) {
        this.editIconService = editIconService;
    }

    @Reference
    protected void setAddressLocalSettingsService(AddressLocalSettingsService addressLocalSettingsService) {
        this.addressLocalSettingsService = addressLocalSettingsService;
    }

    @Reference
    protected void setStaticContextService(StaticContextService staticContextService) {
        this.staticContextService = staticContextService;
    }
}

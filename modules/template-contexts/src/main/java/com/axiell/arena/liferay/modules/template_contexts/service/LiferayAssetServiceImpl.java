package com.axiell.arena.liferay.modules.template_contexts.service;

import com.axiell.arena.liferay.modules.template_contexts.model.liferay.LiferayTagDto;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleResourceLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;
import java.util.stream.Collectors;

@Component(service = LiferayAssetService.class)
public class LiferayAssetServiceImpl implements LiferayAssetService {
    private AssetEntryLocalService assetEntryLocalService;
    private AssetTagLocalService assetTagLocalService;
    private JournalArticleResourceLocalService journalArticleResourceLocalService;
    private GroupLocalService groupLocalService;

    @Override
    public List<AssetCategory> getCategories(JournalArticle journalArticle) throws PortalException {
        AssetEntry entry = assetEntryLocalService.getEntry(journalArticle.getClassName(), journalArticle.getClassPK());
        return entry.getCategories();
    }

    @Override
    public List<LiferayTagDto> getJournalArticleTags(String articleId, long groupId) throws PortalException {
        Group group = groupLocalService.getGroup(groupId);
        String friendlyUrl = group.getFriendlyURL();
        long articleResourcePK = journalArticleResourceLocalService.getArticleResourcePrimKey(groupId, articleId);
        List<AssetTag> tags = assetTagLocalService.getTags(
                "com.liferay.journal.model.JournalArticle", articleResourcePK);
        return tags.stream()
                .map(t -> LiferayTagDto.newInstance(t.getName(), String.format("/web%s", friendlyUrl)))
                .collect(Collectors.toList());
    }

    @Reference
    protected void setAssetEntryLocalService(AssetEntryLocalService assetEntryLocalService) {
        this.assetEntryLocalService = assetEntryLocalService;
    }

    @Reference
    protected void setAssetTagLocalService(AssetTagLocalService assetTagLocalService) {
        this.assetTagLocalService = assetTagLocalService;
    }

    @Reference
    protected void setJournalArticleResourceLocalService(JournalArticleResourceLocalService journalArticleResourceLocalService) {
        this.journalArticleResourceLocalService = journalArticleResourceLocalService;
    }

    @Reference
    protected void setGroupLocalService(GroupLocalService groupLocalService) {
        this.groupLocalService = groupLocalService;
    }
}

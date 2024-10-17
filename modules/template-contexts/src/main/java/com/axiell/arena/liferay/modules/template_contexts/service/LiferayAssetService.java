package com.axiell.arena.liferay.modules.template_contexts.service;

import com.axiell.arena.liferay.modules.template_contexts.model.liferay.LiferayTagDto;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

public interface LiferayAssetService {
    List<AssetCategory> getCategories(JournalArticle journalArticle) throws PortalException;

    List<LiferayTagDto> getJournalArticleTags(String articleId, long groupId) throws PortalException;
}

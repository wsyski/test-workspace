package com.axiell.arena.liferay.modules.template_contexts.model;

import com.axiell.arena.liferay.modules.template_contexts.model.local.RatingDto;
import com.axiell.arena.liferay.modules.template_contexts.service.CatalogueReferenceService;
import com.axiell.arena.liferay.modules.template_contexts.service.DateService;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class StaffPickModel extends AbstractImageModel {

    private String summary;
    private String recordId;
    private String articleAuthor;
    private String articleTitle;
    private String articleText;
    private LocalDate displayDate;
    private String articleId;
    private RecordModel record;
    private CatalogueReferenceService catalogueReferenceService;
    private DateService dateService;

    void setRecord(RecordModel record) {
        this.record = record;
    }

    public boolean hasArticleAuthor() {
        return articleAuthor != null && !articleAuthor.isEmpty();
    }

    public String getArticleId() {
        return articleId;
    }

    void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getDisplayDate(Locale locale) {
        return dateService.d2s(displayDate, locale);
    }

    void setDisplayDate(LocalDate displayDate) {
        this.displayDate = displayDate;
    }

    public String getArticleText() {
        return articleText;
    }

    void setArticleText(String articleText) {
        this.articleText = articleText;
    }

    public String getSummary() {
        return summary;
    }

    void setSummary(String summary) {
        this.summary = summary;
    }

    public String getRecordId() {
        return recordId;
    }

    void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getArticleAuthor() {
        return articleAuthor;
    }

    void setArticleAuthor(String articleAuthor) {
        this.articleAuthor = articleAuthor;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public boolean hasRating() {
        return record.hasRating();
    }

    public RatingDto getRating(boolean fiveStarMax) {
        return record.getRatingDto(fiveStarMax);
    }

    public String getFirstAuthorName() {
        return record.getFirstAuthorName();
    }

    public List<RecordModel> getSimilarRecords(int limit, long groupId) throws IOException, ExecutionException {
        return record.getSimilarRecords(limit, groupId);
    }

    public String getAuthorSearchUrl(String searchPath, long groupId) throws PortalException {
        return catalogueReferenceService.getAuthorSearchUrl(searchPath, groupId, record.getFirstAuthorName());
    }

    public String getCoverImagePath(long groupId) throws ExecutionException {
        return record.getCoverImagePath(groupId);
    }

    public boolean hasAuthor() {
        return record.hasAuthor();
    }

    public String getTitle() {
        return record.getTitle();
    }

    public String getCrdUrl(String resultPath, long groupId) throws PortalException, ExecutionException {
        return record.getCrdUrl(resultPath, groupId);
    }

    public String getAuthor() {
        return record.getFirstAuthorName();
    }

    void setCatalogueReferenceService(CatalogueReferenceService catalogueReferenceService) {
        this.catalogueReferenceService = catalogueReferenceService;
    }

    void setDateService(DateService dateService) {
        this.dateService = dateService;
    }
}

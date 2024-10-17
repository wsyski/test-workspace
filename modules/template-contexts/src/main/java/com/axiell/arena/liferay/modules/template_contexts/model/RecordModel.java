package com.axiell.arena.liferay.modules.template_contexts.model;

import com.axiell.arena.liferay.modules.template_contexts.model.local.FieldsDto;
import com.axiell.arena.liferay.modules.template_contexts.model.local.RatingDto;
import com.axiell.arena.liferay.modules.template_contexts.service.CatalogueReferenceService;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class RecordModel {
    private long id;
    private String uberkey;
    private FieldsDto fields;
    private int rating;
    private CatalogueReferenceService catalogueReferenceService;

    String getSimilarRecordsQuery() {
        ArrayList<String> operands = new ArrayList<>();
        if (fields.hasSubjects()) {
            String subjectBuilder = "(" +
                    fields.getSubjects().stream()
                            .map(s -> String.format("subject:\"%s\"", s))
                            .collect(Collectors.joining(" OR ")) +
                    ")";
            operands.add(subjectBuilder);
        }

        if (fields.hasLanguages()) {
            String languageBuilder = "(" +
                    fields.getLanguages().stream()
                            .map(l -> String.format("language:\"%s\"", l))
                            .collect(Collectors.joining(" OR ")) +
                    ")";
            operands.add(languageBuilder);
        }

        if (fields.hasMediaclass()) {
            operands.add(String.format("mediaclass:\"%s\"", fields.getMediaclass()));
        }

        operands.add(String.format("NOT uberkey:\"%s\"", uberkey));
        return String.join(" AND ", operands);
    }

    public boolean hasAuthor() {
        return fields.hasAuthor();
    }

    public boolean hasRating() {
        return rating != 0;
    }

    public RatingDto getRatingDto(boolean fiveStarMax) {
        return RatingDto.newInstance(fiveStarMax, rating);
    }

    public String getFirstAuthorName() {
        return fields.getAuthors().get(0).getName();
    }

    public String getTitle() {
        return fields.getTitle();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUberkey() {
        return uberkey;
    }

    public void setUberkey(String uberkey) {
        this.uberkey = uberkey;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public FieldsDto getFields() {
        return fields;
    }

    public void setFields(FieldsDto fields) {
        this.fields = fields;
    }

    public List<RecordModel> getSimilarRecords(int limit, long groupId) throws IOException, ExecutionException {
        return catalogueReferenceService.getRecords(getSimilarRecordsQuery(), limit, groupId);
    }

    public String getAuthorSearchUrl(String searchPath, long groupId) throws PortalException {
        return catalogueReferenceService.getAuthorSearchUrl(searchPath, groupId, getFirstAuthorName());
    }

    public String getCoverImagePath(long groupId) throws ExecutionException {
        return catalogueReferenceService.getCoverImagePath(this.id, groupId);
    }

    public String getCrdUrl(String resultPath, long groupId) throws PortalException, ExecutionException {
        return catalogueReferenceService.getCrdUrl(resultPath, id, groupId);
    }

    public String getCrdUrl(long groupId) throws PortalException, ExecutionException {
        return catalogueReferenceService.getCrdUrl(id, groupId);
    }

    public void setCatalogueReferenceService(CatalogueReferenceService catalogueReferenceService) {
        this.catalogueReferenceService = catalogueReferenceService;
    }
}

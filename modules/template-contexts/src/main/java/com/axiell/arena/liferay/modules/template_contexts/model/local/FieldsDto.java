package com.axiell.arena.liferay.modules.template_contexts.model.local;

import java.util.List;

public class FieldsDto {
    private List<String> notes;
    private List<String> languages;
    private String mediaclass;
    private List<String> subjects;
    private String title;
    private String shelfMark;
    private List<String> descriptions;
    private String targetAudience;
    private String publicationYear;
    private String publisher;
    private List<String> coverUrls;
    private String category;
    private List<AuthorDto> authors;

    public boolean hasAuthor() {
        return authors != null && !authors.isEmpty();
    }

    public boolean hasSubjects() {
        return subjects != null && !subjects.isEmpty();
    }

    public boolean hasLanguages() {
        return languages != null && !languages.isEmpty();
    }

    public boolean hasMediaclass() {
        return mediaclass != null && !mediaclass.isEmpty();
    }

    public List<String> getNotes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public String getMediaclass() {
        return mediaclass;
    }

    public void setMediaclass(String mediaclass) {
        this.mediaclass = mediaclass;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShelfMark() {
        return shelfMark;
    }

    public void setShelfMark(String shelfMark) {
        this.shelfMark = shelfMark;
    }

    public List<String> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<String> descriptions) {
        this.descriptions = descriptions;
    }

    public String getTargetAudience() {
        return targetAudience;
    }

    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }

    public String getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public List<String> getCoverUrls() {
        return coverUrls;
    }

    public void setCoverUrls(List<String> coverUrls) {
        this.coverUrls = coverUrls;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<AuthorDto> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorDto> authors) {
        this.authors = authors;
    }
}

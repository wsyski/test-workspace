package com.axiell.arena.liferay.modules.template_contexts.model.local;

public class RecordDto {

    private long id;
    private String uberkey;
    private FieldsDto fields;
    private int rating;

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
}

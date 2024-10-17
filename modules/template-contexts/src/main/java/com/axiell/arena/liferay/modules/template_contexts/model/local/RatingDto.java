package com.axiell.arena.liferay.modules.template_contexts.model.local;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RatingDto {
    public static RatingDto newInstance(boolean fiveStarMax, int rating) {
        RatingDto ratingDto = new RatingDto();
        if (fiveStarMax) {
            ratingDto.setLimit(5);
            ratingDto.setRating(rating / 2);
        } else {
            ratingDto.setLimit(10);
            ratingDto.setRating(rating);
        }
        ArrayList<String> stars = new ArrayList<>();
        for (int i = 0; i < ratingDto.limit; i++) {
            if (i - 0.5 == ratingDto.rating) {
                stars.add("half-full");
            } else if (i < ratingDto.rating) {
                stars.add("full");
            } else {
                stars.add("empty");
            }
        }
        ratingDto.setStars(stars);
        return ratingDto;
    }

    private List<String> stars;
    private int limit;
    private float rating;

    public List<String> getStars() {
        return stars;
    }

    public void setStars(List<String> stars) {
        this.stars = stars;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getRating(Locale locale) {
        return String.format(locale, "%.1f", rating);
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}

package com.axiell.arena.liferay.modules.template_contexts.model;

public abstract class AbstractImageModel extends AbstractViewableModel {

    private String image;
    private String altText;

    public String getThumbnail(int thumbId) {
        return String.format("%s&imageThumbnail=%d", image, thumbId);
    }

    public boolean hasImage() {
        return image != null && !image.isEmpty();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }
}

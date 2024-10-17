package com.axiell.arena.liferay.modules.template_contexts.model.liferay;

public class LiferayTagDto {
    public static LiferayTagDto newInstance(String name, String href) {
        LiferayTagDto liferayTagDto = new LiferayTagDto();
        liferayTagDto.setHref(href);
        liferayTagDto.setName(name);
        return liferayTagDto;
    }

    private String name;
    private String href;

    LiferayTagDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}

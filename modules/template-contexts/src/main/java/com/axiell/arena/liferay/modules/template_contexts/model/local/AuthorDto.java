package com.axiell.arena.liferay.modules.template_contexts.model.local;

public class AuthorDto {
    private String name;
    private String role;

    public AuthorDto() {
    }

    public AuthorDto(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

package com.axiell.arena.liferay.modules.overrides.dynamic_include_top_js;

import com.axiell.authinfo.Patron;
import java.util.Set;

public class PatronDTO {
    private final Patron patron;
    private final Long arenaAgencyMemberId;
    private final String nick;
    private final String token;

    public PatronDTO(final Patron patron, final Long arenaAgencyMemberId, final String nick, final String token) {
        this.patron = patron;
        this.arenaAgencyMemberId = arenaAgencyMemberId;
        this.nick = nick;
        this.token = token;
    }

    public String getId() {
        return patron.getId();
    }

    public Long getArenaUserId() {
        return patron.getArenaUserId();
    }

    public Set<String> getRoles() {
        return patron.getRoles();
    }

    public String getDisplayName() {
        return patron.getName();
    }

    public Long getArenaAgencyMemberId() {
        return arenaAgencyMemberId;
    }

    public String getNick() {
        return nick;
    }

    public String getToken() {
        return token;
    }
}

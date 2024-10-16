package com.axiell.arena.liferay.modules.overrides.dynamic_include_top_js;

import com.axiell.authinfo.Patron;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class PatronDTOFactory {

    public static PatronDTO get(final Object agencyUser) {
        PatronDTO patronDTO = null;
        if (agencyUser != null) {
            Long arenaUserId = getArenaUserId(agencyUser);
            Long arenaAgencyMemberId = getArenaAgencyMemberId(agencyUser);
            String id = getId(agencyUser);
            String nick = getNick(agencyUser);
            String name = getName(agencyUser);
            Set<String> roles = getRoles(agencyUser);
            String token = getToken(agencyUser);
            if (arenaUserId != null || id != null) {
                Patron patron = new Patron.Builder().id(id).arenaUserId(arenaUserId).name(name).roles(roles).build();
                patronDTO = new PatronDTO(patron, arenaAgencyMemberId, nick, token);
            }
        }
        return patronDTO;
    }

    private static Long getArenaUserId(final Object agencyUser) {
        Object object = getValue(agencyUser, "getId");
        return object == null ? null : (Long) getValue(object, "getValue");
    }

    private static Long getArenaAgencyMemberId(final Object agencyUser) {
        Object object = getValue(agencyUser, "getSelectedArenaMemberId");
        return object == null ? null : (Long) object;
    }

    private static Set<String> getRoles(final Object agencyUser) {
        Object object = getValue(agencyUser, "getRoles");
        return object == null ? null : (Set<String>) object;
    }

    private static String getId(final Object agencyUser) {
        Object object = getValue(agencyUser, "getSelectedExternalUserId");
        return object == null ? null : object.toString();
    }

    private static String getToken(final Object agencyUser) {
        Object object = getValue(agencyUser, "getSelectedToken");
        return object == null ? null : object.toString();
    }

    private static String getNick(final Object agencyUser) {
        Object object = getValue(agencyUser, "getNick");
        return object == null ? null : object.toString();
    }

    private static String getName(final Object agencyUser) {
        Object object = getValue(agencyUser, "getDisplayName");
        return object == null ? null : object.toString();
    }

    private static Object getValue(final Object object, final String methodName) {
        Method method = getMethod(object, methodName);
        if (method == null) {
            return null;
        }
        Object value = null;
        try {
            value = method.invoke(object);
        } catch (IllegalAccessException | InvocationTargetException ex) {
            return null;
        }
        return value;
    }

    private static Method getMethod(final Object object, final String methodName) {
        if (object == null) {
            return null;
        }
        try {
            return object.getClass().getMethod(methodName);
        } catch (NoSuchMethodException ex) {
            return null;
        }
    }

}

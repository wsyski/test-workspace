package com.axiell.arena.liferay.modules.arena.util;

import com.liferay.portal.kernel.util.URLCodec;

public class EncodingUtil {
    public static String encodePath(final String value) {
        return URLCodec.encodeURL(value, true);
    }
}

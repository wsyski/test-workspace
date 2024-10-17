package com.axiell.arena.liferay.modules.overrides.k10_search_portlet.constants;

public class K10SearchPortletKeys {
    public static final String K10_SEARCH_PORTLET_BUNDLE_NAME = "k10-search-portlet";
    public static final String K10_SEARCH_PORTLET_NAME = "k10_search_portlet";

    public static final String KEY_VIEW_MODE = "viewMode";
    public static final String KEY_BASE_URL = "baseUrl";
    public static final String KEY_K10_SEARCH_PAGE = "searchPage";
    public static final String KEY_SEARCH_QUERY_PARAMETER = "searchQueryParameter";
    public static final String KEY_PAGE_SIZE = "pageSize";
    public static final String KEY_COLLECTION = "collection";

    public static final int DEFAULT_PAGE_SIZE = 8;
    public static final String DEFAULT_BASE_URL = "https://bookit.axiell.com/k10/index/axiell";
    public static final String DEFAULT_K10_SEARCH_PAGE = "k10-search";
    public static final String DEFAULT_SEARCH_QUERY_PARAMETER = "p_r_p_arena_urn:arena_search_query";
    public static final String[] DEFAULT_COLLECTION = new String[]{"medline", "NL", "JSTOR", "springer", "DOAJ", "elsevier", "CTG"};
    public static final int[] PAGE_SIZES = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 15, 18, 20, 25, 50};
    public static final String[] COLLECTIONS = new String[]{
            "GVK",
            "OLC",
            "medline",
            "SWB",
            "NL",
            "JSTOR",
            "springer",
            "WTI",
            "DOAJ",
            "elsevier",
            "ÖVK",
            "CTG",
            "KFL",
            "biorXiv",
            "chemrXiv",
            "engrXiv"
    };
    public static final String PREFERENCES_POSTFIX = "--";
    public static final String LABEL_POSTFIX = ".label";
    public static final String TITLE_POSTFIX = ".title";
}

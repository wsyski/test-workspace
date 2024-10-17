package com.axiell.arena.liferay.modules.template_contexts.service;

import com.axiell.arena.liferay.modules.arena.model.LMSSearchResultResponse;

public interface ArenaLocalServiceClient {

    LMSSearchResultResponse getLMSSearchResult(String query, String type, Integer start, Integer size) throws Exception;
}

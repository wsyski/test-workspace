package com.axiell.arena.liferay.modules.template_contexts.api;

import com.liferay.asset.kernel.model.AssetEntry;

import java.util.Map;

public interface ModelFactory {

    Object newInstance(Map<String, Object> dataModel) throws Exception;

    Object newInstance(AssetEntry assetEntry) throws Exception;
}

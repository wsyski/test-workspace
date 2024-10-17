package com.axiell.arena.liferay.modules.template_contexts.service;

import com.axiell.arena.liferay.modules.template_contexts.model.central.CentralSettingsDto;

import java.util.concurrent.ExecutionException;

public interface CentralSettingsCacheService {
    CentralSettingsDto getCentralSettings(Long groupId) throws ExecutionException;
}

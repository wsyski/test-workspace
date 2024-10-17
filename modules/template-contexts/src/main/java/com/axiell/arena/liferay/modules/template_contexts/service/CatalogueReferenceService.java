package com.axiell.arena.liferay.modules.template_contexts.service;

import com.axiell.arena.liferay.modules.template_contexts.model.RecordModel;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface CatalogueReferenceService {
    String getCoverImagePath(long recordId, long groupId) throws ExecutionException;

    String getSearchUrl(String searchPath, String query, long groupId) throws PortalException;

    String getAuthorSearchUrl(String searchPath, long groupId, String authorName) throws PortalException;

    String getCrdUrl(String resultPath, long recordId, long groupId) throws PortalException, ExecutionException;

    String getCrdUrl(long recordId, long groupId) throws PortalException, ExecutionException;

    List<RecordModel> getRecords(String query, int limit, long groupId) throws IOException, ExecutionException;

    List<RecordModel> getRecords(String query, int limit, long groupId, String sortField, String sortDirection) throws IOException, ExecutionException;

    RecordModel getRecord(long recordId, long groupId) throws IOException, ExecutionException;

    RecordModel getRecord(long recordId, long groupId,String sortField, String sortDirection) throws IOException, ExecutionException;

}

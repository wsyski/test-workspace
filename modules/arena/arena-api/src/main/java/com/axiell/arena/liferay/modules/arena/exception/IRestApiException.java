package com.axiell.arena.liferay.modules.arena.exception;


import com.axiell.arena.liferay.modules.arena.error.RestApiError;

public interface IRestApiException {
    RestApiError getRestApiError();

}

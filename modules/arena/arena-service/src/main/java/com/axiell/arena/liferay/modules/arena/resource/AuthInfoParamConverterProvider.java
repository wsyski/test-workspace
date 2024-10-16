package com.axiell.arena.liferay.modules.arena.resource;

import com.axiell.authinfo.AuthInfo;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

@Provider
public class AuthInfoParamConverterProvider implements ParamConverterProvider {

    public AuthInfoParamConverterProvider(final ParamConverter<AuthInfo> authInfoConverter) {
        this.authInfoConverter = authInfoConverter;
    }

    private final ParamConverter<AuthInfo> authInfoConverter;

    @Override
    public <T> ParamConverter<T> getConverter(final Class<T> rawType, final Type genericType, final Annotation[] annotations) {
        if (rawType.getName().equals(AuthInfo.class.getName())) {
            return (ParamConverter<T>) authInfoConverter;
        }
        return null;
    }
}

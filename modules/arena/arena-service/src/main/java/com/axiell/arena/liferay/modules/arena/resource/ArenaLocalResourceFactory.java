package com.axiell.arena.liferay.modules.arena.resource;

import com.axiell.arena.liferay.modules.arena.configuration.ArenaSystemConfiguration;
import com.axiell.arena.liferay.modules.arena.util.ArenaUtil;
import com.axiell.authinfo.AuthInfoConverter;
import com.axiell.authinfo.IAuthHeaderSecretKeyResolver;
import com.axiell.authinfo.jwt.JwtAuthHeaderParser;
import org.apache.cxf.jaxrs.client.WebClient;

import java.util.Collections;
import java.util.Locale;

import static com.axiell.authinfo.IAuthHeaderParser.BEARER_SCHEME;

public class ArenaLocalResourceFactory extends BaseResourceFactory {
    public static final ArenaLocalResourceFactory INSTANCE = new ArenaLocalResourceFactory();

    public ArenaLocalResourceFactory() {
        super();
    }

    public ArenaLocalResourceFactory(final IAuthHeaderSecretKeyResolver authHeaderSecretKeyResolver) {
        super();
        JwtAuthHeaderParser authHeaderParser = new JwtAuthHeaderParser();
        authHeaderParser.setAuthHeaderSecretKeyResolver(authHeaderSecretKeyResolver);
        AuthInfoConverter authInfoConverter = new AuthInfoConverter();
        authInfoConverter.setAuthHeaderParsers(Collections.singletonMap(BEARER_SCHEME, authHeaderParser));
        authInfoConverter.setDefaultScheme(BEARER_SCHEME);
        AuthInfoParamConverterProvider authInfoParamConverterProvider = new AuthInfoParamConverterProvider(authInfoConverter);
        getClientProviders().add(authInfoParamConverterProvider);
    }

    public <T> T createResource(final Class<T> clazz, Locale locale) {
        ArenaSystemConfiguration arenaSystemConfiguration = ArenaUtil.getArenaSystemConfiguration();
        String localApiEndpoint = arenaSystemConfiguration.localApiEndpoint();
        T proxy = super.createResource(localApiEndpoint, clazz);
        if (locale != null) {
            WebClient.client(proxy).acceptLanguage(locale.toLanguageTag());
        }
        return proxy;
    }

    public <T> T createResource(final Class<T> clazz) {
        return createResource(clazz, null);
    }
}

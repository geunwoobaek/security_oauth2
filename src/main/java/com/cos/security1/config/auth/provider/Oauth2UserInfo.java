package com.cos.security1.config.auth.provider;

public interface Oauth2UserInfo {
    String getProviderId();

    String getProvider();

    String getEmail();

    String getName();
}

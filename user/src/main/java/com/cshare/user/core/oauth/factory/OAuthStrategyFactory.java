package com.cshare.user.core.oauth.factory;

import com.cshare.user.core.oauth.OAuthProvider;
import com.cshare.user.core.oauth.OAuthStrategy;

public interface OAuthStrategyFactory {
    OAuthStrategy create(OAuthProvider provider);
}

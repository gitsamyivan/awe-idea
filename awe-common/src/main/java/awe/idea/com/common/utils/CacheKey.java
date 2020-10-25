package awe.idea.com.common.utils;

public class CacheKey {
    //session使用的key
    public static class SessionKey{
        public static final String ADMIN_SESSION_USER = "AWE_ADMIN_USER";

        public static final String API_REQUEST_ATTR_USER = "AWE_API_ATTR_USER";

        public static final String WEB_SESSION_USER = "AWE_WEB_USER";
    }

    //redis存储使用的key
    public static class RedisCacheKey{
        public static final String ADMIN_SSO_REDIS_KEY = "AWE_ADMIN_SSO_HASHSET";

        public static final String API_SSO_REDIS_KEY = "AWE_API_SSO_HASHSET";
        public static final String API_CAPTCHA_REDIS_KEY = "AWE_API_CAPTCHA_HASHSET";

        public static final String WEB_SSO_REDIS_KEY = "AWE_WEB_SSO_HASHSET";
    }

    //zookeeper存储使用的key
    public static class ZookeeperCacheKey{
        public static final String ADMIN_LOCK_PROFIT = "/aweidea/admin/curator/lock/";

        public static final String API_LOCK_PROFIT = "/aweidea/api/curator/lock/";

        public static final String WEB_LOCK_PROFIT = "/aweidea/web/curator/lock/";
    }
}

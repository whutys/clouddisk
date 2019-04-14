package cn.clouddisk.config;

import cn.clouddisk.shiro.realm.UserRealm;
import cn.clouddisk.shiro.web.filter.LogoutFilter;
import cn.clouddisk.utils.StringUtils;
import net.sf.ehcache.CacheManager;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.config.ConfigurationException;
import org.apache.shiro.io.ResourceUtils;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Value("${shiro.user.loginUrl}")
    private String loginUrl;
    @Value("${shiro.user.indexUrl}")
    private String indexUrl;
    @Value("${shiro.user.unauthorizedUrl}")
    private String unauthorizedUrl;
    @Value("${shiro.session.expireTime}")
    private int expireTime;
    @Value("${shiro.session.validationInterval}")
    private int validationInterval;
    @Value("${shiro.cookie.domain}")
    private String domain;
    @Value("${shiro.cookie.path}")
    private String path;
    @Value("${shiro.cookie.httpOnly}")
    private boolean httpOnly;
    @Value("${shiro.cookie.maxAge}")
    private int maxAge;

    public LogoutFilter logoutFilter() {
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setLoginUrl(loginUrl);
        return logoutFilter;
    }

    /**
     * shiro过滤器配置
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl(loginUrl);
        shiroFilterFactoryBean.setSuccessUrl(indexUrl);
        shiroFilterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/signOut", "logout");
        filterChainDefinitionMap.put(loginUrl, "anon");
//        filterChainDefinitionMap.put("/**","authc");

        /*过滤器*/
        Map<String, Filter> filter = new LinkedHashMap<>();
//        filter.put("onlineSession",onlineSessionFilter());
        filter.put("logout", logoutFilter());
        shiroFilterFactoryBean.setFilters(filter);

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager(UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);

        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
//        自定义缓存
        securityManager.setCacheManager(ehCacheManager());
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    @Bean
    public EhCacheManager ehCacheManager() {
        CacheManager cacheManager = CacheManager.getCacheManager("clouddisk");
        EhCacheManager ehCacheManager = new EhCacheManager();
        if (StringUtils.isNull(cacheManager)) {
            ehCacheManager.setCacheManager(new CacheManager(getCacheManagerConfigFileInputStream()));
            return ehCacheManager;
        } else {
            ehCacheManager.setCacheManager(cacheManager);
            return ehCacheManager;
        }
    }

    protected InputStream getCacheManagerConfigFileInputStream() {
        String configFile = "classpath:ehcache/ehcache-shiro.xml";
        InputStream inputStream = null;
        try {
            inputStream = ResourceUtils.getInputStreamForPath(configFile);
            byte[] bytes = IOUtils.toByteArray(inputStream);
            InputStream in = new ByteArrayInputStream(bytes);
            return in;
        } catch (IOException e) {
            throw new ConfigurationException("Unable to obtain input stream for cacheManagerConfigFile [" + configFile + "[", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    @Bean
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return userRealm;
    }

    /**
     * 加密规则
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(3);
//        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);//默认
        return hashedCredentialsMatcher;
    }

    @Bean
    public RememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        cookieRememberMeManager.setCipherKey(Base64.decode("fcq+/xW488hMTCD+cmJ3aq=="));
        return cookieRememberMeManager;
    }

    @Bean
    public SimpleCookie rememberMeCookie() {
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        cookie.setDomain(domain);
        cookie.setPath(path);
        cookie.setHttpOnly(httpOnly);
        cookie.setMaxAge(maxAge * 24 * 60 * 60);
        return cookie;
    }

    /**
     * 注解通知
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 生命周期管理
     */
    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    private String onlineSessionFilter() {
        return null;
    }
}

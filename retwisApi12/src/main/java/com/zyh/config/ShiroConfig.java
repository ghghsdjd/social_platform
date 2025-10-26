package com.zyh.config;




import com.zyh.filter.JWTFilter;
import com.zyh.shiro.CustomRealm;
import com.zyh.shiro.cache.RedisCacheManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ShiroConfig: Shiro の設定クラス。どのリクエストをフィルタするか、許可設定などはここで行う
 *
 */

/**
 * @description  Shiro 設定クラス
 */
@Configuration
public class ShiroConfig {
    /**
     * まず token フィルタを通し、リクエストヘッダに token が存在する場合はその token で login を実行し、Realm で検証する
     */
    @Bean
    public ShiroFilterFactoryBean factory(SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

        // カスタムフィルタを追加し、名前を "jwt" とする
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        // カスタムの JWT フィルタを設定
        filterMap.put("jwt", new JWTFilter());
        factoryBean.setFilters(filterMap);
        factoryBean.setSecurityManager(securityManager);
        // 権限なしの場合にリダイレクトする URL を設定
        factoryBean.setUnauthorizedUrl("/unauthorized/无权限");
        Map<String, String> filterRuleMap = new HashMap<>();
        // すべてのリクエストをカスタム JWT Filter を通す
        filterRuleMap.put("/**", "jwt");

        // 認可不要なインターフェースを許可する
        // Swagger のインターフェースを許可する
        filterRuleMap.put("/v2/api-docs","anon");
        filterRuleMap.put("/swagger-resources/configuration/ui","anon");
        filterRuleMap.put("/swagger-resources","anon");
        filterRuleMap.put("/swagger-resources/configuration/security","anon");
        filterRuleMap.put("/swagger-ui.html","anon");
        filterRuleMap.put("/webjars/**","anon");
        // ログインやその他認可不要なインターフェースを許可する
        filterRuleMap.put("/login", "anon");
        filterRuleMap.put("/register","anon");
        filterRuleMap.put("/findPassword","anon");
        filterRuleMap.put("/sendEmail","anon");
        filterRuleMap.put("/websocket","anon");
        filterRuleMap.put("/unauthorized/**", "anon");
        factoryBean.setFilterChainDefinitionMap(filterRuleMap);
        return factoryBean;

    }

    /**
     * securityManager を注入する
     */
    @Bean
    public SecurityManager securityManager(CustomRealm customRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // カスタム realm を設定する
        customRealm.setAuthenticationCachingEnabled(true);// 开启认证缓存
        customRealm.setAuthorizationCachingEnabled(true);// 开启授权缓存
        // 使用自定义的redis 进行缓存
        customRealm.setCacheManager(new RedisCacheManager());// 设置缓存管理器
        customRealm.setCacheManager(new RedisCacheManager());
        customRealm.setCachingEnabled(true);//开启全局缓存
        customRealm.setAuthenticationCachingEnabled(true);//认证缓存
        customRealm.setAuthenticationCacheName("AuthenticationCache");
        customRealm.setAuthorizationCachingEnabled(true);//授权缓存
        customRealm.setAuthorizationCacheName("AuthorizationCache");
        securityManager.setRealm(customRealm);

        /*
         * 关闭shiro自带的session
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }

    /**
     * 注解支持を追加する
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


}

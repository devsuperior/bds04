package com.devsuperior.bds04.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.Arrays;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    private Environment env;
    @Autowired
    private TokenStore tokenStore;


    /* STRINGS COM AS REGRAS */
    private static final String[] PUBLIC = {"/events/**", "/cities/**"};
    private static final String[] CLIENT = {"/events/**"};


    /**
     * Recebe o token da aplicação
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore);
    }

    /**
     * Configura as regras de acessos para os endpoints com segurança ou sem
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {

        /** CONFIGURAÇÃO PARA LIBERAÇÃO DO H2 */
        if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            http.headers().frameOptions().disable();
        }

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, PUBLIC).permitAll()
                .antMatchers(HttpMethod.POST, CLIENT).hasAnyRole("CLIENT", "ADMIN")
                .anyRequest().hasAnyRole("ADMIN");
    }
}

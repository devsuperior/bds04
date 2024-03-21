package com.devsuperior.bds04.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private Environment environment;

    @Autowired
    private JwtTokenStore tokenStore;

    private static final String[] PUBLIC = { "/oauth/token", "/h2-console/**"};
    private static final String[] PUBLIC_GET = {"/events/**", "/cities/**" };
    private static final String[] EVENTS_POST = { "/events" };
    private static final String[] CITIES_POST = { "/cities" };

    private static final String[] ADMIN = { "/events", "/cities" };
    public ResourceServerConfig() {
        super();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //liberando o h2
       if(Arrays.asList(environment.getActiveProfiles()).contains("test")) {
           http.headers().frameOptions().disable();
       }

        http.authorizeRequests()
                .antMatchers(PUBLIC).permitAll()
                .antMatchers(HttpMethod.GET, PUBLIC_GET).permitAll()
                .antMatchers(HttpMethod.POST, EVENTS_POST).hasAnyRole("CLIENT", "ADMIN")
                .antMatchers(HttpMethod.POST, CITIES_POST).hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, ADMIN).hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, ADMIN).hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, ADMIN).hasRole("ADMIN")
                .anyRequest().authenticated();
    }
}

//ANA -> CLIENTE
//BOB -> ADMIN

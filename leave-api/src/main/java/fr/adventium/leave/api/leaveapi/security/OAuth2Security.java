package fr.adventium.leave.api.leaveapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;



@Configuration
//@EnableAuthorizationServer
public class OAuth2Security extends AuthorizationServerConfigurerAdapter   {
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Value("${resource.id:spring-boot-application}")
    private String resourceId;
    
    
	
	@Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        return new JwtAccessTokenConverter();
    }
	
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    	security.tokenKeyAccess("isAnonymous() || hasAuthority('ROLE_CLIENT')")
         .checkTokenAccess("hasAuthority('ROLE_CLIENT')");
    }
 
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient("leave-client")
                .authorizedGrantTypes("client-credentials", "password","refresh_token")
                .authorities("ROLE_CLIENT")
                .scopes("read", "write", "trust")
                .resourceIds(this.resourceId)
                .accessTokenValiditySeconds(5000)
                .secret("leave-secret").refreshTokenValiditySeconds(50000);
    }
 
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
        		.accessTokenConverter(accessTokenConverter())
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
        ;
    }
	
}

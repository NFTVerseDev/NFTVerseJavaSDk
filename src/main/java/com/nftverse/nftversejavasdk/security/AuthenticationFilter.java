package com.nftverse.nftversejavasdk.security;

import com.nftverse.nftversejavasdk.dto.AppTokenMasterDto;
import com.nftverse.nftversejavasdk.dto.UserValidDTO;
import com.nftverse.nftversejavasdk.services.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationFilter extends GenericFilterBean {
    private static final String TOKEN_SESSION_KEY = "token";
    private static final String USER_SESSION_KEY = "user";
    private static final String X_AUTH_TOKEN = "X-Auth-Token";
    private static final String X_APP_TOKEN = "X-App-Token";
    private static final String X_SOURCE = "X-Source";
    private static final String DEFAULT_SOURCE = "Default";
    public static final String APP_TOKEN = "E9FE46D9-FC53-480F-9DC6-D26A7DE233A01";
    private static final String authenticationUrl = "http://localhost:8080/authenticate";
    private AuthenticationService authenticationService;

    private final static Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    public AuthenticationFilter(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = asHttp(request);
        HttpServletResponse httpResponse = asHttp(response);
        Optional<String> token = Optional.ofNullable(httpRequest.getHeader(X_AUTH_TOKEN));
        Optional<String> appToken = Optional.ofNullable(httpRequest.getHeader(X_APP_TOKEN));
        Optional<String> source = Optional.ofNullable(httpRequest.getHeader(X_SOURCE));
        try {
            if (token.isPresent()) {
                UserValidDTO user = this.authenticationService.authenticate(token.get());
                    if (user != null) {
                        List<GrantedAuthority> authorities = new ArrayList<>();
                        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                        UsernamePasswordAuthenticationToken value = new UsernamePasswordAuthenticationToken(user, user, authorities);
                        value.setDetails(new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) request));
                        SecurityContextHolder.getContext().setAuthentication(value);
                    } else {
                        throw new Exception("Incorrect auth token");

                    }

            } else if (appToken.isPresent()) {
                AppTokenMasterDto userAppToken=this.authenticationService.authenticateAppToken(appToken.get());
                if(userAppToken!=null){
                    List<GrantedAuthority> authorities = new ArrayList<>();
                    authorities.add(new SimpleGrantedAuthority("ROLE_APPUSER"));
                    UsernamePasswordAuthenticationToken value = new UsernamePasswordAuthenticationToken(userAppToken, userAppToken, authorities);
                    value.setDetails(new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) request));
                    SecurityContextHolder.getContext().setAuthentication(value);
                } else {
                    throw new Exception("Incorrect App Token");
                }
            }
            chain.doFilter(request, response);

        } catch (Exception ex) {
            if(ex.getMessage().equals("Incorrect auth token") || ex.getMessage().equals("Permission denied!") || ex.getMessage().equals("Incorrect App Token")) {
                logger.error("doFilter: error while authenticating user",ex);
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
            } else {
                logger.error("doFilter: error ",ex);
                httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
            }
        } finally {
            SecurityContextHolder.clearContext();
        }

    }

    private void addSessionContextToLogging() {

    }

    private HttpServletRequest asHttp(ServletRequest request) {
        return (HttpServletRequest) request;
    }

    private HttpServletResponse asHttp(ServletResponse response) {
        return (HttpServletResponse) response;
    }

}

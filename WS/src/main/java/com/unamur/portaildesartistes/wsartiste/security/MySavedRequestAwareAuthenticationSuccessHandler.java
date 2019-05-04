package com.unamur.portaildesartistes.wsartiste.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class MySavedRequestAwareAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(MySavedRequestAwareAuthenticationSuccessHandler.class);

    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null) {
            logger.error("Session : "+ authentication.getPrincipal().toString());
            //LoggedUser user = new LoggedUser(authentication.getName(), activeUserStore);
            session.setAttribute("username", authentication.getPrincipal().toString() );
        }else{
            logger.error("Session pas OK : "+ authentication.getPrincipal().toString());
        }
        SavedRequest savedRequest = requestCache.getRequest(request, response);
//response.encodeRedirectURL("/");
//authentication.setAuthenticated(true);
        //logger.error(authentication.getPrincipal().toString() );
        //logger.error(authentication.getCredentials().toString() );
        //authentication.setAuthenticated(true); //set Granted...
        if (savedRequest == null) {
            clearAuthenticationAttributes(request);
            return;
        }
        String targetUrlParam = getTargetUrlParameter();
        if (isAlwaysUseDefaultTargetUrl()
                || (targetUrlParam != null
                && StringUtils.hasText(request.getParameter(targetUrlParam)))) {
            requestCache.removeRequest(request, response);
            clearAuthenticationAttributes(request);
            return;
        }
        clearAuthenticationAttributes(request);

    }

    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }
}

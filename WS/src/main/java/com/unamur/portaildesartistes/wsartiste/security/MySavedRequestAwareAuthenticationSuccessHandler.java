package com.unamur.portaildesartistes.wsartiste.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class MySavedRequestAwareAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{
    private static final Logger logger = LoggerFactory.getLogger(MySavedRequestAwareAuthenticationSuccessHandler.class);

    private RequestCache requestCache = new HttpSessionRequestCache();

/*    private void set(HttpServletRequest request,
                     HttpServletResponse response,
                     Authentication authentication)
                throws ServletException, IOException {
        super.onAuthenticationSuccess(request, response, authentication);
        HttpSession session = request.getSession(false);
        if(session!=null){
            logger.error("Session OK");
            session.setAttribute("userName", authentication.getName() );
            //response.addHeader("Allow","POST");
            //response.setStatus( HttpStatus.SC_OK );
            response.addCookie( new Cookie( "JSESSIONID",session.getId() ) );
        }else
            logger.error("Session KO");
    }*/

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws ServletException, IOException {

        SavedRequest savedRequest = requestCache.getRequest(request, response);

        super.onAuthenticationSuccess(request, response, authentication);
        HttpSession session = request.getSession(false);
        if(session!=null){
            logger.error("Session OK");
            session.setAttribute("userName", authentication.getName() );
            //response.addHeader("Allow","POST");
            //response.setStatus( HttpStatus.SC_OK );
            response.addCookie( new Cookie( "JSESSIONID",session.getId() ) );
        }else
            logger.error("Session KO");

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

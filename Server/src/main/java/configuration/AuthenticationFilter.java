package configuration;

import io.jsonwebtoken.Claims;
import model.User;
import service.AuthService;
import util.ResponseUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    private final Set<String> securedEndpoints = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        securedEndpoints.add("/TaskMentor/api/user");
        securedEndpoints.add("/TaskMentor/api/logout");
        securedEndpoints.add("/TaskMentor/websocket");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();

        if (isSecuredEndpoint(requestURI)) {
            String accessToken = extractAccessToken(httpRequest);

            if (accessToken != null) {
                if (authenticateTokens(accessToken, httpRequest)) {
                    chain.doFilter(request, response);
                    return;
                }
            }
            ResponseUtil.sendErrorResponse(httpResponse, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized access");
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean isSecuredEndpoint(String requestURI) {
        return securedEndpoints.contains(requestURI);
    }

    public static String extractAccessToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    private boolean authenticateTokens(String accessToken, HttpServletRequest request) {
        try {
            if (accessToken != null) {
                // Verify access token
                Claims claims = AuthService.verifyToken(accessToken);
                if (claims != null) {
                    // Extract user information from token
                    User user = AuthService.getUserIdAndRoleFromClaimAccessToken(claims);

                    // Save user in session
                    HttpSession session = request.getSession();
                    session.setAttribute("currentUser", user);
                    System.out.println("session = " + session);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void destroy() {}
}

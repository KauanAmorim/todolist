package br.com.kauanamorim.todolist.interceptor;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.kauanamorim.todolist.filter.Public;
import br.com.kauanamorim.todolist.user.IUserRepository;
import br.com.kauanamorim.todolist.user.UserModel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.Base64;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler
    ) throws Exception {
        if (this.isPublicRoute(handler) || request.getServletPath().startsWith("/h2-console")) {
            return true;
        }
        return this.Authenticate(request, response);
    }

    private boolean isPublicRoute(Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        boolean hasMethodAnnotationPublic = handlerMethod.getMethodAnnotation(Public.class) != null;
        boolean hasPublicAnnotationPresent = handlerMethod.getBeanType().isAnnotationPresent(Public.class);
        return hasPublicAnnotationPresent || hasMethodAnnotationPublic;
    }

    private boolean Authenticate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(this.hasAInvalidAuthorizationHeader(request)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        String[] credentials = this.getCredentials(request);
        String username = credentials[0];
        String password = credentials[1];

        UserModel user = this.userRepository.findByUsername(username);
        boolean userDoesNotExists = user == null;
        if (userDoesNotExists) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        char[] inputPassword = password.toCharArray();
        String storedPassword = user.getPassword();
        if (this.isInvalidCredentials(inputPassword, storedPassword)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        request.setAttribute("idUser", user.getId());
        return true;
    }

    private boolean hasAInvalidAuthorizationHeader(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        boolean hasAValidAuthorizationHeader = authorization != null && authorization.startsWith("Basic ");
        return !hasAValidAuthorizationHeader;
    }

    private String[] getCredentials(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        String authEncoded = authorization.substring("Basic".length()).trim();
        byte[] authDecoded = Base64.getDecoder().decode(authEncoded);
        String authString = new String(authDecoded);
        return authString.split(":");
    }

    private boolean isInvalidCredentials(char[] inputPassword, String storedPassword) {
        BCrypt.Result passwordVerify = BCrypt.verifyer().verify(inputPassword, storedPassword);
        return !passwordVerify.verified;
    }
}

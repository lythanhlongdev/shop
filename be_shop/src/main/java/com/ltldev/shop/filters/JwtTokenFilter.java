package com.ltldev.shop.filters;
import com.ltldev.shop.components.JwtTokenUtil;
import com.ltldev.shop.models.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Value("${api.prefix}")
    private String apiPrefix;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            if(isBypassToken(request)) {
                filterChain.doFilter(request, response); //enable bypass
                return;
            }
            final String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                return;
            }
            final String token = authHeader.substring(7);
            final String phoneNumber = jwtTokenUtil.extractPhoneNumber(token);
            if (phoneNumber != null
                    && SecurityContextHolder.getContext().getAuthentication() == null) {
                User userDetails = (User) userDetailsService.loadUserByUsername(phoneNumber);
                if(jwtTokenUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request, response); //enable bypass
        }catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }

    }
    private boolean isBypassToken(@NonNull HttpServletRequest request) {
        final List<Pair<String, String>> bypassTokens = Arrays.asList(
                Pair.of(String.format("%s/roles", apiPrefix), "GET"),
                Pair.of(String.format("%s/products", apiPrefix), "GET"),
                Pair.of(String.format("%s/categories", apiPrefix), "GET"),
                Pair.of(String.format("%s/users/register", apiPrefix), "POST"),
                Pair.of(String.format("%s/users/login", apiPrefix), "POST")
        );

        String requestPath = request.getServletPath();
        String requestMethod = request.getMethod();

        if (requestPath.equals(String.format("%s/orders", apiPrefix))
                && requestMethod.equals("GET")) {
            // Allow access to %s/orders
            return true;
        }
        for (Pair<String, String> bypassToken : bypassTokens) {
            if (requestPath.contains(bypassToken.getLeft())
                    && requestMethod.equals(bypassToken.getRight())) {
                return true;
            }
        }

        return false;
    }




//    protected void doFilterInternal(
//            @NotNull HttpServletRequest request,
//            @NotNull HttpServletResponse response,
//            @NotNull FilterChain filterChain)
//            throws ServletException, IOException {
//        try {
//            if (isByPassTokens(request)) {
//                filterChain.doFilter(request, response);
//                return;
//            }
//            final String authHeader = request.getHeader("Authorization");
//            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
//                return;
//            }
//            final String token = authHeader.substring(7);
//            final String phoneNumber = extractPhoneNumber(token);
//            if (phoneNumber != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                User existingUserDetails = (User) userDetailsService.loadUserByUsername(phoneNumber);
//                if (jwtTokenUtil.validateToken(token, existingUserDetails)) {
//                    UsernamePasswordAuthenticationToken authenticationToken =
//                            new UsernamePasswordAuthenticationToken(
//                                    existingUserDetails,
//                                    null,
//                                    existingUserDetails.getAuthorities()
//                            );
//                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//                }
//            }
//            filterChain.doFilter(request, response);
//        } catch (Exception e) {
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
//        }
//
//    }
//
//
//    private boolean isByPassTokens(@NotNull HttpServletRequest request) {
//        // Lists function and HttpMethod not auth
//        final List<Pair<String, String>> bypassTokens = Arrays.asList(
//                Pair.of(String.format("/%s/products", url), "GET"),
//                Pair.of(String.format("/%s/categories", url), "GET"),
//                Pair.of(String.format("/%s/users/register", url), "POST"),
//                Pair.of(String.format("/%s/users/login", url), "POST")
//        );
//        /*
//            get path and httpMethod in request equals bypassToken in bypassTokens
//               left is path  and  right is httpMethod
//        */
//        for (Pair<String, String> bypassToken : bypassTokens) {
//            if (request.getServletPath().contains(bypassToken.getLeft()) &&
//                    request.getMethod().equals(bypassToken.getRight())) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//
//    public String extractPhoneNumber(String token) {
//        return jwtTokenUtil.extractClaim(token, Claims::getSubject);
//    }


//    @Override
//    protected void doFilterInternal(
//            @NotNull HttpServletRequest request,
//            @NotNull HttpServletResponse response,
//            @NotNull FilterChain filterChain)
//            throws ServletException, IOException {
//        try {
//            if (isByPassTokens(request)) {
//                filterChain.doFilter(request, response);
//                return;
//            }
//            final String authHeader = request.getHeader("Authorization");

//            if (authHeader != null && authHeader.startsWith("Bearer ")) {
//                final String token = authHeader.substring(7);
//                final String phoneNumber = extractPhoneNumber(token);
//                if (phoneNumber != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                    UserDetails existingUserDetails = userDetailsService.loadUserByUsername(phoneNumber);
//                    if (jwtTokenUtil.validateToken(token, existingUserDetails)) {
//                        UsernamePasswordAuthenticationToken authenticationToken =
//                                new UsernamePasswordAuthenticationToken(
//                                        existingUserDetails,
//                                        null,
//                                        existingUserDetails.getAuthorities()
//                                );
//                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//                    }
//                }
//            }
//            filterChain.doFilter(request, response);
//        } catch (Exception e) {
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
//        }
//
//    }
}

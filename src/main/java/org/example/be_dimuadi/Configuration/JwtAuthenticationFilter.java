package org.example.be_dimuadi.Configuration;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import java.io.IOException;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.example.be_dimuadi.Exception.AppException;
import org.example.be_dimuadi.Exception.ErrorCode;
import org.example.be_dimuadi.Persitence.Entity.Users;
import org.example.be_dimuadi.Persitence.Responsitory.UserReponsitory;
import org.example.be_dimuadi.Service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Service
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtServices jwtServices;
    private final UserService userService;
    private final UserReponsitory userReponsitory;
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String authHeader = req.getHeader("Authorization");
        if (authHeader == null ) {
            chain.doFilter(req, res);
            return;
        }
        log.info("AuthHeaer {}",authHeader);
        if(jwtServices.isTokenExpired(authHeader))
        {
            throw new AppException(ErrorCode.TOKEN_EXPIRED,"Mã truy cập hết hạn");
        }
        try {
            String userId = jwtServices.extractUsername(authHeader);
            if (userId == null) {
                chain.doFilter(req, res);
                return;
            }
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                Users user = userReponsitory.findById(UUID.fromString(userId)).orElse(null);
                if (user == null) {
                    chain.doFilter(req, res);
                    return;
                }

                UserDetails userDetails = userService.loadUserByUsername(user.getUsername());

                if (jwtServices.ValidateToken(authHeader, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userId, null, userDetails.getAuthorities()
                            );
                    authentication.setDetails(new WebAuthenticationDetails(req));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            log.error("JWT Filter error: {}", e.getMessage());
        }

        chain.doFilter(req, res);
    }
}

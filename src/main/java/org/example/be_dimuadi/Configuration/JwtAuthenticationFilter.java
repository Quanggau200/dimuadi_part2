package org.example.be_dimuadi.Configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.example.be_dimuadi.Service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

@Service
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtServices jwtServices;
    private final UserService userService;
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String token=req.getHeader("Authorization");
        if(token==null || !token.startsWith("Bearer "))
        {
            chain.doFilter(req, res);
            return;
        }
        if("OPTIONS".equals(req.getMethod())){
            res.setHeader("Access-Control-Allow-Origin", "*");
            res.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(req, res); // cho đi tiếp
            return;
        }
        try {
            String username=jwtServices.extractUsername(token);
            if(username==null){
                chain.doFilter(req, res);
            }
                UserDetails userDetails=userService.loadUserByUsername(username);
                if(jwtServices.ValidateToken(token,userDetails)){
                    UsernamePasswordAuthenticationToken authentication =new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                                userDetails.getAuthorities());
                    authentication .setDetails(new WebAuthenticationDetails(req));
                    SecurityContextHolder.getContext().setAuthentication(authentication );
                }
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
        chain.doFilter(req, res);
    }

    public String getCookieValue(HttpServletRequest request, String cookieName) {
        if(request.getCookies()==null){
            return null;
        }
        else {
            request.getCookies();
        }
        for (Cookie cookie : request.getCookies()) {
            if(cookie.getName().equals(cookieName)){
                return cookie.getValue();
            }
        }
        return null;


    }
}

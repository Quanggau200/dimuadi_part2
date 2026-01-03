package org.example.be_dimuadi.Configuration;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.be_dimuadi.Persitence.Entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class JwtServices {

    @Value("${jwt.sign-key}")
    private String SignKey;
    @Value("${jwt.access-token.expiration}")
    private Long  AccessTokenExpiration;
    @Value("${jwt.refresh-token.expiration}")
    private Long RefreshTokenExpiration;

    private SecretKey secretKey;

    private SecretKey getSignKey()
    {
        if(secretKey==null){
            byte[] keyBytes=SignKey.getBytes(StandardCharsets.UTF_8);
            if(keyBytes.length<32){
                throw new IllegalArgumentException(
                        "JWT signing key must be at least 256 bits (32 characters) long"
                );
            }
            secretKey=Keys.hmacShaKeyFor(keyBytes);
        }
        return secretKey;
    }
    // gen refresh token
    public String GenerateRefreshToken(Users user)
    {
        Map<String,Object> claims=new HashMap<>();
        claims.put("user_id",user.getUserId());
        claims.put("phone",user.getPhone());
        claims.put("email",user.getEmail());
        claims.put("sub",user.getUsername());
        return CreateToken(claims,user.getUsername(),RefreshTokenExpiration);
    }
    // gen access token
    public String GenerateAccessToken(Users user)
    {
        Map<String,Object> claims=new HashMap<>();
        claims.put("user_id",user.getUserId());
        claims.put("phone",user.getPhone());
        claims.put("sub",user.getUsername());
        return CreateToken(claims,user.getUsername(),AccessTokenExpiration);
    }
    //gen token core
    public String CreateToken(Map<String,Object> claims,String subject,long expiration )
    {
        Date now =new Date();
        Date expirationDate=new Date(now.getTime() + expiration*1000);
        try{
            return Jwts.builder()
                    .claims(claims)
                    .issuedAt(now)
                    .subject(subject)
                    .signWith(getSignKey())
                    .expiration(expirationDate)
                    .compact();
        }
        catch (Exception e)
        {
            log.error("Exception caught while trying to generate JWT Token");
            throw new RuntimeException(e);
        }

    }
    // giải mã token theo tên
    public String extractUsername(String token)
    {
        try{
            return extractClaim(token,Claims::getSubject);
        }
        catch (Exception e)
        {
            log.warn("Failed to extract username from token: {}", e.getMessage());
            return null;
        }
    }

    public <T> T extractClaim(String token, Function<Claims,T> clazz)
    {
        final Claims claims=extractAllClaims(token);
        return clazz.apply(claims);
    }
    // giai mã token
    public Claims extractAllClaims(String token)
    {
        try {
            return Jwts.parser()
                    .verifyWith(getSignKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }
        catch (Exception e)
        {
            log.warn(e.getMessage());
            return null;
        }
    }
    public boolean ValidateToken(String token, UserDetails user)
    {
       try{
            final String username=extractUsername(token);
            boolean isValid=username!=null && username.equals(user.getUsername()) && !isTokenExpired(token);
            if(isValid)
            {
                log.debug("Username {} has been validated",username);
            }
            else
            {
                log.warn("Token validation failed for user: {}", username);
            }
            return isValid;
       }
       catch (Exception e)
       {
           throw new RuntimeException(e);
       }
    }
    // kiểm tra xem token hết hạn hay chưa
    public boolean isTokenExpired(String token)
    {
       try{
           Date expirationDate=extractClaim(token,Claims::getExpiration);
           return  expirationDate!=null && expirationDate.before(new Date());
       }
       catch (Exception e)
       {
           log.error("Error checking token expiration: {}", e.getMessage());
           return true;
       }

    }
    public Date extractExpiration(String token)
    {
        return extractClaim(token, Claims::getExpiration);
    }
    public Date getIssuedAtDate(String token)
    {
        return extractClaim(token,Claims::getIssuedAt);
    }
    public boolean canTokenBeRefreshed(String token)
    {
        try{
            return !isTokenExpired(token);
        }
        catch (Exception e)
        {
            return false;
        }
    }
    public boolean isValidTokenFormat(String token)
    {
        if(token==null || token.trim().isEmpty())
        {
            return false;
        }
        String[] parts=token.split("\\.");
        return parts.length==3;
    }

}

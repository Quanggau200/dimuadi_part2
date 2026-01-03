package org.example.be_dimuadi.Service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.be_dimuadi.Persitence.Entity.Users;
import org.example.be_dimuadi.Persitence.Responsitory.UserReponsitory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal=true)
public class qUserService implements UserDetailsService {
    private UserReponsitory userReponsitory;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users=userReponsitory.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(username));
        return org.springframework.security.core.userdetails.
                User.withUsername(users.getUsername())
                .password(users.getPassword())
                .authorities("USER", users.getRoles().getRoleName())
                .build();
    }

}

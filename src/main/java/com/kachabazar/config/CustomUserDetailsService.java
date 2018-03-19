package com.kachabazar.config;



import com.kachabazar.user.UserRepository;
import com.kachabazar.user.WebUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service("userDetailsService")
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;



    @Autowired
    private HttpServletRequest request;

    public CustomUserDetailsService() {
        super();
    }

    // API

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final String ip = getClientIP();


        try {
            final WebUser webUser = userRepository.findByUsername(username);

            if (webUser == null) {
                throw new UsernameNotFoundException("No user found with email or phone: " + username);
            }

            final List<GrantedAuthority> grantedAuthorities = getGrantedAuthorities(webUser.getRoles());
//            for(GrantedAuthority ga :grantedAuthorities){
//                System.out.println(ga.getAuthority().);
//            }
            User user = new org.springframework.security.core.userdetails.User(webUser.getUsername(), webUser.getPassword(), webUser.isEnabled(), true, true, true, grantedAuthorities);
            //System.out.println(user.getAuthorities().toString());
            return user;
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }


    private final List<GrantedAuthority> getGrantedAuthorities(final List<String> roles) {
        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (final String role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role));
        }
        return authorities;
    }

    private final String getClientIP() {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}

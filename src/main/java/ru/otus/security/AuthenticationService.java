package ru.otus.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.AppUser;
import ru.otus.exception.NoEntityException;
import ru.otus.repository.UserRepository;

import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class AuthenticationService implements UserDetailsService {

    private final UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        AppUser appUser=null;
        try {
            appUser = Optional.of(userRepository.findUserByName(s)).orElseThrow(NoEntityException::new);
        } catch (NoEntityException e) {
            log.error("No such user exists");
            e.printStackTrace();
        }
        return new User(appUser.getName(),appUser.getEncryptedPassword()
                , appUser.getRoles().stream().map(role->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));
    }
}

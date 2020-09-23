package rostyk.stupnytskiy.andromeda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.AccountLoginRequest;
import rostyk.stupnytskiy.andromeda.dto.request.AccountRegistrationRequest;
import rostyk.stupnytskiy.andromeda.dto.response.AccountResponse;
import rostyk.stupnytskiy.andromeda.dto.response.AuthenticationResponse;
import rostyk.stupnytskiy.andromeda.entity.Account;
import rostyk.stupnytskiy.andromeda.entity.UserRole;
import rostyk.stupnytskiy.andromeda.repository.AccountRepository;
import rostyk.stupnytskiy.andromeda.security.JwtTokenTool;
import rostyk.stupnytskiy.andromeda.security.JwtUser;

import java.io.IOException;


@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenTool jwtTokenTool;


    @Autowired
    private BCryptPasswordEncoder encoder;


    public AuthenticationResponse register(AccountRegistrationRequest request) throws IOException {
        if (userRepository.existsByLogin(request.getLogin())) {
            throw new BadCredentialsException("User with username " + request.getLogin() + " already exists");
        }
        Account account = new Account();
        account.setLogin(request.getLogin());
        account.setUserRole(UserRole.ROLE_USER);
        account.setPassword(encoder.encode(request.getPassword()));
        account.setUsername(request.getUsername());
        userRepository.save(account);
        return login(registrationToLogin(request));
    }

    public AuthenticationResponse login(AccountLoginRequest request) {
        String login = request.getLogin();
        Account account = findByLogin(login);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, request.getPassword()));
        String token = jwtTokenTool.createToken(login, account.getUserRole());
        String name = account.getUsername();
        Long id = account.getId();
        return new AuthenticationResponse(name,token,id);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Account account = findByLogin(login);
        return new JwtUser(account.getLogin(), account.getUserRole(), account.getPassword());
    }

    public Account findByLogin(String username)  {
        return userRepository.findByLogin(username).orElseThrow(() -> new UsernameNotFoundException("User with login " + username + " not exists"));
    }

    public Account findById(Long id)  {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User with id " + id + " not exists"));
    }


    private AccountLoginRequest registrationToLogin(AccountRegistrationRequest registrationRequest){
        AccountLoginRequest loginRequest = new AccountLoginRequest();
        loginRequest.setLogin(registrationRequest.getLogin());
        loginRequest.setPassword(registrationRequest.getPassword());
        return loginRequest;
    }

    public AccountResponse getUserById(Long id) {
        return new AccountResponse(findById(id));
    }
}

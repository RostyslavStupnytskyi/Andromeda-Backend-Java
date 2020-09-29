package rostyk.stupnytskiy.andromeda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.account.AccountLoginRequest;
import rostyk.stupnytskiy.andromeda.dto.request.account.AccountRegistrationRequest;
import rostyk.stupnytskiy.andromeda.dto.response.AccountResponse;
import rostyk.stupnytskiy.andromeda.dto.response.AuthenticationResponse;
import rostyk.stupnytskiy.andromeda.entity.Account;
import rostyk.stupnytskiy.andromeda.entity.Seller;
import rostyk.stupnytskiy.andromeda.entity.User;
import rostyk.stupnytskiy.andromeda.entity.UserRole;
import rostyk.stupnytskiy.andromeda.mail.MailService;
import rostyk.stupnytskiy.andromeda.repository.AccountRepository;
import rostyk.stupnytskiy.andromeda.security.JwtTokenTool;
import rostyk.stupnytskiy.andromeda.security.JwtUser;
import rostyk.stupnytskiy.andromeda.tools.ConfirmationCodeGenerator;
import rostyk.stupnytskiy.andromeda.tools.FileTool;

import java.io.IOException;


@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenTool jwtTokenTool;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private FileTool fileTool;

    @Autowired
    private ConfirmationCodeGenerator confirmationCodeGenerator;

    @Autowired
    private MailService mailService;

    public AuthenticationResponse registerUser(AccountRegistrationRequest request) throws IOException {
        return register(request, UserRole.ROLE_USER);
    }

    public AuthenticationResponse registerSeller(AccountRegistrationRequest request) throws IOException {
        return register(request, UserRole.ROLE_SELLER);
    }

    public Boolean checkoutConfirmationCode(String code) {
        Account account = findByLogin((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if (account.getConfirmationCode().equals(code)) {
            account.setIsConfirmed(true);
            account.setConfirmationCode(null);
            accountRepository.save(account);
            return true;
        } else return false;
    }

    public AuthenticationResponse login(AccountLoginRequest request) {
        String login = request.getLogin();
        Account account = findByLogin(login);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, request.getPassword()));
        String token = jwtTokenTool.createToken(login, account.getUserRole());

        String name = account.getUsername();
        Long id = account.getId();

        return new AuthenticationResponse(name, token, id);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Account account = findByLogin(login);
        return new JwtUser(account.getLogin(), account.getUserRole(), account.getPassword());
    }

    public Account findByLogin(String login) {
        return accountRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("User with login " + login + " not exists"));
    }

    public Account findById(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User with id " + id + " not exists"));
    }

    public AccountResponse getUserById(Long id) {
        return new AccountResponse(findById(id));
    }

    private AuthenticationResponse register(AccountRegistrationRequest request, UserRole userRole) throws IOException {
        if (accountRepository.existsByLogin(request.getLogin())) {
            throw new BadCredentialsException("User with username " + request.getLogin() + " already exists");
        }
        Account account = new Account();
        account.setLogin(request.getLogin());
        account.setPassword(encoder.encode(request.getPassword()));
        account.setUsername(request.getUsername());
        account.setEmail(request.getEmail());
        account.setUserRole(userRole);
        account.setConfirmationCode(confirmationCodeGenerator.createCode());
        mailService.registerMain(request.getEmail(), account.getConfirmationCode());
        if (userRole == UserRole.ROLE_USER) account.setUser(new User());
        else if (userRole == UserRole.ROLE_SELLER) account.setSeller(new Seller());

        if (request.getAvatar() != null) {
            account.setAvatar(fileTool.saveUserAvatarImage(request.getAvatar(), request.getLogin()));
        }

        accountRepository.save(account);
        return login(registrationToLogin(request));
    }


    private AccountLoginRequest registrationToLogin(AccountRegistrationRequest registrationRequest) {
        AccountLoginRequest loginRequest = new AccountLoginRequest();
        loginRequest.setLogin(registrationRequest.getLogin());
        loginRequest.setPassword(registrationRequest.getPassword());
        return loginRequest;
    }
}

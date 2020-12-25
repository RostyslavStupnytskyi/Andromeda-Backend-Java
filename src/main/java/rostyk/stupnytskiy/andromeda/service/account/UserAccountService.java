package rostyk.stupnytskiy.andromeda.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
import rostyk.stupnytskiy.andromeda.repository.UserRepository;

@Service
public class UserAccountService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountService accountService;

    public UserAccount findBySecurityContextHolder(){
        return findById(accountService.getIdBySecurityContextHolder());
    }

    public UserAccount findById(Long id){
        return userRepository.findById(id).orElseThrow(IllegalAccessError::new);
    }
}

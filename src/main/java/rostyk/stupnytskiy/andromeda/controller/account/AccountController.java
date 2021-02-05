package rostyk.stupnytskiy.andromeda.controller.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.andromeda.dto.request.account.AccountDataRequest;
import rostyk.stupnytskiy.andromeda.dto.request.account.AccountLoginRequest;
import rostyk.stupnytskiy.andromeda.dto.response.account.AccountResponse;
import rostyk.stupnytskiy.andromeda.dto.response.AuthenticationResponse;
import rostyk.stupnytskiy.andromeda.service.account.AccountService;
import rostyk.stupnytskiy.andromeda.service.AccountStatisticsService;

import javax.validation.Valid;
import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountStatisticsService accountStatisticsService;

//    @GetMapping
//    private String test() {
//        return accountService.testAuth();
//    }

    @PostMapping("/login")
    public AuthenticationResponse login(@Valid @RequestBody AccountLoginRequest request) {
        return accountService.login(request);
    }


    @PostMapping("/register/user")
    public AuthenticationResponse registerUser(@Valid @RequestBody AccountLoginRequest request) throws IOException {
        return accountService.registerUser(request);
    }

    @PostMapping("/register/goods-seller")
    public AuthenticationResponse registerSeller(@Valid @RequestBody AccountLoginRequest request) throws IOException {
        return accountService.registerGoodsSeller(request);
    }

    @PutMapping("/update")
    public void updateAccountData(@Valid @RequestBody AccountDataRequest request){
        accountService.updateAccountData(request);
    }

    @PutMapping("/confirm")
    public Boolean confirmCode(String code){
        return accountStatisticsService.confirmRegistrationCode(code);
    }

    @GetMapping("/checkToken")
    public void checkToken() {
    }

    @GetMapping("/getProfile")
    private <T extends AccountResponse>AccountResponse getAccountResponse(Long id){
        return accountService.getAccountResponse(id);
    }
}













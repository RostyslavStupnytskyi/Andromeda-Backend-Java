package rostyk.stupnytskiy.andromeda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.andromeda.dto.request.mail.MailRequest;
import rostyk.stupnytskiy.andromeda.mail.MailService;

@CrossOrigin
@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping("/main")
    public void saveMain(MailRequest request){
        mailService.saveMain(request);
    }

    @PostMapping()
    public void save(MailRequest request){
        mailService.save(request);
    }

//    @ResponseBody
//    @GetMapping("/send")
//    public String sendEmail(String target) {
//        mailService.registerMain(target);
//        return "Ярік всьо працює";
//    }
}

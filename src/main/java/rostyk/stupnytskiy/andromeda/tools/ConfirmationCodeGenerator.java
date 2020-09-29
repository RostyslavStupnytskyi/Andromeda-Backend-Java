package rostyk.stupnytskiy.andromeda.tools;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class ConfirmationCodeGenerator {

    public String createCode(){
        Random random = new Random();
        String code = "";
        for (int i = 0; i < 8; i++)
            if (random.nextBoolean()) code+=getCodeSymbol();
            else code+=getCodeNumber();

        return code;
    }

    private char getCodeSymbol(){
        Random random = new Random();
        char randomChar = (char) ('a' + random.nextInt(26));
        if (random.nextBoolean()) randomChar = Character.toUpperCase(randomChar);
        return randomChar;
    }

    private char getCodeNumber(){
        Random random = new Random();
        char randomChar = (char) ('0' + random.nextInt(9));
        return randomChar;
    }
}

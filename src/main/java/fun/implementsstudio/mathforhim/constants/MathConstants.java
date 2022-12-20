package fun.implementsstudio.mathforhim.constants;


import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class MathConstants {

    public static String MATH_GEN_PREFIX = "MATH-GENERATE:";
    public static String MATH_CODE_PREFIX = "MATH-";
    public static List<String> invitationCodeList = null;
    @PostConstruct
    public void initInvitationCode(){
        invitationCodeList = new ArrayList<>();
        for (int i=1;i<=20;i++){
            invitationCodeList.add(MATH_CODE_PREFIX+i);
        }
    }

}

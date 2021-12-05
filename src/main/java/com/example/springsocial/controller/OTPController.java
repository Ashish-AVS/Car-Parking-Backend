package com.example.springsocial.controller;

import com.example.springsocial.service.MyEmailService;
import com.example.springsocial.service.OtpService;
import com.example.springsocial.util.EmailTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Controller
public class OTPController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public OtpService otpService;

    @Autowired
    public MyEmailService myEmailService;

    @GetMapping("/generateOtp")
    public String generateOtp(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        int otp = otpService.generateOTP(username);

        logger.info("OTP : "+otp);

//Generate The Template to send OTP
        EmailTemplate template = new EmailTemplate("SendOtp.html");

        Map<String,String> replacements = new HashMap<String,String>();
        replacements.put("user", username);
        replacements.put("otpnum", String.valueOf(otp));

        String message = template.getTemplate(replacements);

        myEmailService.sendOtpMessage("shrisowdhaman@gmail.com", "OTP -SpringBoot", message);

        return "otppage";
    }

    @RequestMapping(value ="/validateOtp", method = RequestMethod.GET)
    public @ResponseBody
    String validateOtp(@RequestParam("otpnum") int otpnum){

        final String SUCCESS = "Entered Otp is valid";

        final String FAIL = "Entered Otp is NOT valid. Please Retry!";

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        logger.info(" Otp Number : "+otpnum);

//Validate the Otp
        if(otpnum >= 0){
            int serverOtp = otpService.getOtp(username);

            if(serverOtp > 0){
                if(otpnum == serverOtp){
                    otpService.clearOTP(username);
                    return ("Entered Otp is valid");
                }else{
                    return SUCCESS;
                }
            }else {
                return FAIL;
            }
        }else {
            return FAIL;
        }
    }
}

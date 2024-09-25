package org.example.humanity.authentication.utility;

import lombok.RequiredArgsConstructor;
import org.example.humanity.authentication.entity.User;
import org.example.humanity.authentication.repo.AuthenticationRepo;

import java.security.SecureRandom;
import java.util.Optional;

import org.example.humanity.common.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenerateOTP {

    private final AuthenticationRepo repo;


    public String generateOTP() {
        int length = 6;
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < length; i++) {
            otp.append(random.nextInt(10));
        }

        return otp.toString();
    }

    public String verifyOTP(String userId, String otp) {
        Optional<User> userOTP = repo.findById(Long.valueOf(userId));
        if (userOTP.get().getToken().equals(otp)) {
            repo.clearOtpByOtp(otp);
        } else throw new ResourceNotFoundException("You provided a wrong otp");


        return "Otp Verified";
    }

}

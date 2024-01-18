package com.jack.webapp.controllers.authentication;

import com.jack.webapp.domain.dto.LoginUserDto;
import com.jack.webapp.domain.dto.RegisterUserDto;
import com.jack.webapp.domain.entities.UserEntity;
import com.jack.webapp.services.AuthenticationService;
import com.jack.webapp.services.JwtService;
import com.jack.webapp.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private  final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserEntity> register(@RequestBody RegisterUserDto registerUserDto) {
        UserEntity registeredUser = authenticationService.signup(registerUserDto);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public  ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) throws Exception {
        UserEntity authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = LoginResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpiration()).build();
        return new ResponseEntity<>(loginResponse, HttpStatus.ACCEPTED);
    }

    @PostMapping("/refresh-jwt")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request, response);
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody Long id, @RequestBody Long postId) {
        if(authenticationService.verifyUser(id, postId)) {
            return ResponseEntity.ok("Account verified seccessfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verification failed");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody AuthenticationRequest request) {
        boolean resetStatus = userService.resetPassword(request.getEmailAddress());
        if (resetStatus) {
            return ResponseEntity.ok("A little present has been sent to your email. ;)");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to reset password.");
        }
    }

    @GetMapping("/login")
    public String showLoginForm(Model model){
        model.addAttribute("loginUser", new LoginUserDto());
        return "login";
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model){
        model.addAttribute("user", new RegisterUserDto());
        return "signup";
    }

    @GetMapping("/runGoScript")
    public String runGoScript() {
        try {
            // Replace "your_script.go" with the actual name of your Go script
            Process process = Runtime.getRuntime().exec("src/main/resources/shitpost2/main.go");

            // You can read the output of the script if needed
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                return "Go script executed successfully. Output:\n" + output.toString();
            } else {
                return "Error executing Go script. Exit code: " + exitCode;
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }



}

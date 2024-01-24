package com.jack.webapp.controllers.authentication;

import com.jack.webapp.domain.dto.LoginUserDto;
import com.jack.webapp.domain.dto.RegisterUserDto;
import com.jack.webapp.domain.entities.UserEntity;
import com.jack.webapp.mappers.Mapper;
import com.jack.webapp.services.AuthenticationService;
import com.jack.webapp.services.JwtService;
import com.jack.webapp.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Log
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private  final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final Mapper<UserEntity, RegisterUserDto> signupMapper;
    private final Mapper<UserEntity, LoginUserDto> loginMapper;


    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterUserDto registerUserDto) {
        authenticationService.signup(signupMapper.mapFrom(registerUserDto));
        return new ResponseEntity<>("Account created. Check e-mail.", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginUserDto request) throws Exception {
        AuthenticationResponse authReposponse = authenticationService.authenticate(request);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + authReposponse.getAccessToken());
        return new ResponseEntity<>(authReposponse, headers, HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
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

    @GetMapping("/forgotten-password")
    public ResponseEntity<?> forgottenPassword(@RequestBody AuthenticationRequest request) {
        boolean resetStatus = userService.resetPassword(request.getEmailAddress());
        if(resetStatus) {
            return ResponseEntity.ok("A present with verification link has been sent to your email. ;)");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to reset password.");
        }
    }



//    @PostMapping("/forgot-password")
//    public ResponseEntity<?> forgotPassword(@RequestBody AuthenticationRequest request) {
//        boolean resetStatus = userService.resetPassword(request.getEmailAddress());
//        if (resetStatus) {
//            return ResponseEntity.ok("A present with verification link has been sent to your email. ;)");
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to reset password.");
//        }
//    }

//    @GetMapping("/runGoScript")
//    public String runGoScript() {
//        try {
//            // Replace "your_script.go" with the actual name of your Go script
//            Process process = Runtime.getRuntime().exec("src/main/resources/shitpost2/main.go");
//
//            // You can read the output of the script if needed
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            StringBuilder output = new StringBuilder();
//            String line;
//            while ((line = reader.readLine()) != null) {
//                output.append(line).append("\n");
//            }
//            int exitCode = process.waitFor();
//
//            if (exitCode == 0) {
//                return "Go script executed successfully. Output:\n" + output.toString();
//            } else {
//                return "Error executing Go script. Exit code: " + exitCode;
//            }
//
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//            return "Error: " + e.getMessage();
//        }
//    }



}

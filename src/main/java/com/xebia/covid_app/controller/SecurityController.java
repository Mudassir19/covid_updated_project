package com.xebia.covid_app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xebia.covid_app.entities.User;
import com.xebia.covid_app.models.LoginResponse;
import com.xebia.covid_app.models.Password;
import com.xebia.covid_app.models.UserNamesResponse;
import com.xebia.covid_app.models.UserResponse;
import com.xebia.covid_app.models.Username;
import com.xebia.covid_app.service.EmailService;
import com.xebia.covid_app.service.TempUsername;
import com.xebia.covid_app.service.UserService;
import com.xebia.covid_app.service.impl.MyUserDetailsService;
import com.xebia.covid_app.util.JwtUtil;

@CrossOrigin("*")
@RestController
public class SecurityController {

    private static final String CLASS_NAME = SecurityController.class.getName();

    private static final Logger LOGGER = LoggerFactory.getLogger(CLASS_NAME);

    @Autowired
    private TempUsername tempUsername;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    @RequestMapping({"/superadmin"})
    public String user() {
        return "SuperAdmin";
    }

    @RequestMapping({"/admin"})
    public String admin() {
        return "Admin";
    }

    @PostMapping("/username")
    public ResponseEntity<UserResponse> verifyUsername(@RequestBody Username username) throws Exception {
        UserResponse userResponse = new UserResponse();
        try {
            userDetailsService.loadUserByUsername(username.getUsername());
            emailService.sendEmail(username.getUsername());
            UserDetails userDetails = userDetailsService.loadUserByUsername(username.getUsername());
            tempUsername.setUsername(username.getUsername());
            userResponse.setMessage("user verified and mail send: ");
            userResponse.setStatus("success");
            return ResponseEntity.status(HttpStatus.OK).body(userResponse);
        } catch (NoSuchElementException e) {
            userResponse.setMessage("invalid user");
            userResponse.setStatus("failure");
//            throw new Exception("Incorrect username",e);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(userResponse);
        }
    }

    @PostMapping("/password")
    public ResponseEntity<LoginResponse> verifyPassword(@RequestBody Password password) throws Exception {
        LoginResponse userResponse = new LoginResponse();
        LoginResponse.Payload payload = userResponse.new Payload();
        LoginResponse.Payload.User userjson = payload.new User();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(tempUsername.getUsername(), password.getPassword()));
            final UserDetails userDetails = userDetailsService.loadUserByUsername(tempUsername.getUsername());

            final String jwt = jwtTokenUtil.generateToken(userDetails);
            Optional<User> user = userService.findUser(tempUsername.getUsername());

            userjson.setEmail(user.get().getUsername());
            userjson.setFirstName(user.get().getFirstName());
            userjson.setLastName(user.get().getLastName());
            userjson.setLocation(user.get().getLocation().getId());
            userjson.setId(user.get().getId());

            userjson.setRole(user.get().getRoles());

            payload.setUser(userjson);

            payload.setToken(jwt);
            userResponse.setPayload(payload);

            userResponse.setStatus("success");
            userResponse.setMessage("Otp verified");
            return ResponseEntity.status(HttpStatus.OK).body(userResponse);
        } catch (BadCredentialsException e) {
            userResponse.setStatus("failure");
            userResponse.setMessage("Invalid OTP");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userResponse);
        }
    }

    @GetMapping(value = "/allusers")
    public ResponseEntity<UserResponse> getUsers() {
        List<User> usersList = userService.findAllUsers();
        int len = usersList.size();
        UserNamesResponse userNamesResponse = null;
        List<UserNamesResponse> listnames = new ArrayList<UserNamesResponse>();
        UserResponse userResponse = new UserResponse();
        UserResponse.Payload payload = userResponse.new Payload();
        for (int i = 0; i < len; i++) {

            userNamesResponse = new UserNamesResponse();

            userNamesResponse.setId(usersList.get(i).getId());
            userNamesResponse.setUsername(usersList.get(i).getFirstName());

            listnames.add(userNamesResponse);
        }

        if ((!listnames.isEmpty()) && (listnames.size() > 0)) {
            LOGGER.info("UsersName:" + listnames);
            userResponse.setStatus("success");
            userResponse.setMessage("successfully get the list of users:");
            payload.setObjectList(listnames);
            userResponse.setPayload(payload);
            return ResponseEntity.status(HttpStatus.OK).body(userResponse);
        } else {
            userResponse.setStatus("failure");
            userResponse.setMessage("users are not available:");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userResponse);
        }
    }
}

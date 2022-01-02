package com.amk.lms.controllers;

import com.amk.lms.exceptions.EtAuthException;
import com.amk.lms.exceptions.EtInternalServerErrorException;
import com.amk.lms.exceptions.EtResourceNotFoundException;
import com.amk.lms.models.req.users.LoginReq;
import com.amk.lms.models.req.users.UserReq;
import com.amk.lms.models.res.users.LoginRes;
import com.amk.lms.models.res.users.UserRes;
import com.amk.lms.services.TokenDtoService;
import com.amk.lms.services.impl.UserDetailServiceImpl;
import com.amk.lms.services.impl.UserServiceImpl;
import com.amk.lms.utils.JwtUtility;
//import io.swagger.annotations.ApiOperation;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationNotSupportedException;
import java.util.List;
@RestController // Compare between @Controller VS @RestController
@RequestMapping("/user/v1/")
public class UserController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private JwtUtility jwtUtility;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailServiceImpl userDetailsService;
    @Autowired
    private TokenDtoService tokenDtoService;
    @PostMapping("login")
    public  ResponseEntity<LoginRes> login(@RequestBody LoginReq loginReq) throws EtAuthException{
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginReq.getUserName(),
                            loginReq.getPassword())
            );
        } catch (AuthenticationException ex){
            throw new EtAuthException("Login Failure." + ex.getMessage());
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(
                loginReq.getUserName());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        //query from db or get through request
        String aud = userService.findUser(loginReq.getUserName()).getChannelCode();
        final String token = jwtUtility.generateToken(userDetails, authentication.getAuthorities(), aud);
        //final String refreshToken = tokenDtoService.createRefreshToken(token, userService.validateUser(userDetails.getUsername(),userDetails.getPassword()).getUserId());
        return new ResponseEntity<LoginRes>( new LoginRes(token),HttpStatus.OK);
    }

    @PostMapping("users/create")
    public ResponseEntity<UserRes> addUser(@RequestBody UserReq req) throws EtAuthException{
        try{
            req.setPassword(BCrypt.hashpw(req.getPassword(), BCrypt.gensalt(10)));
            UserRes userRes = userService.addUser(req);
            return new ResponseEntity<UserRes>(userRes,HttpStatus.OK);
        }catch (Exception ex){
            throw new EtInternalServerErrorException(ex.getMessage().toString());
        }
    }
    @GetMapping("users")
    public ResponseEntity<List<UserRes>> getUsers() throws EtAuthException{
        try{
            List<UserRes> userResList = userService.findAll();
            return new ResponseEntity<List<UserRes>>(userResList, HttpStatus.OK);

        }catch (EtAuthException ex){
            throw new EtResourceNotFoundException(ex.getMessage().toString());
        }
    }
    @GetMapping("users/{id}")
    public ResponseEntity<UserRes> getUserById(@PathVariable("id") Integer id) throws EtAuthException{
        try{
            UserRes userRes = userService.findUserById(id);
            return new ResponseEntity<UserRes>(userRes, HttpStatus.OK);

        }catch (EtAuthException ex){
            throw new EtResourceNotFoundException(ex.getMessage().toString());
        }
    }
}

package com.amk.lms.services.impl;

import com.amk.lms.Repositories.UserRepository;
import com.amk.lms.exceptions.EtAuthException;
import com.amk.lms.exceptions.EtInternalServerErrorException;
import com.amk.lms.exceptions.EtResourceNotFoundException;
import com.amk.lms.models.entities.User;
import com.amk.lms.models.req.users.UserReq;
import com.amk.lms.models.res.users.UserRes;
import com.amk.lms.services.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    private  final UserRepository userRepo;

    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserRes validateUser(String username, String password) throws EtAuthException {
        try {
            UserRes res = new UserRes();
            User user = userRepo.findByEmail(username);

            if (!BCrypt.checkpw(password, user.getPassword()))
                throw new EtAuthException("Invalid email/password");

            res.setEmail(user.getEmail());
            res.setUserId(user.getUserId());
            res.setFirstName(user.getFirstName());
            res.setLastName(user.getLastName());
            res.setRoles(user.getRoles());
            return res;

        } catch (Exception ex) {
            throw new EtAuthException(ex.getMessage());
        }
    }

    @Override
    public List<UserRes> findAll() throws EtResourceNotFoundException {
        try {
            List<UserRes> userListRes = new ArrayList<>();
            List<User> userList = userRepo.findAll();

            for (int i = 0; i <= userList.size() - 1; i++) {
                UserRes user = new UserRes();
                user.setEmail(userList.get(i).getEmail());
                user.setUserId(userList.get(i).getUserId());
                user.setFirstName(userList.get(i).getFirstName());
                user.setLastName(userList.get(i).getLastName());
                user.setRoles(userList.get(i).getRoles());
                user.setChannelCode(userList.get(i).getChannelCode());
                userListRes.add(user);
            }
            return userListRes;

        } catch (Exception ex) {
            throw new EtResourceNotFoundException(ex.getMessage());
        }
    }

    @Override
    public UserRes addUser(UserReq user) throws EtInternalServerErrorException {
        try {
            User _req = new User();
            UserRes res = new UserRes();
            _req.setEmail(user.getEmail());
            _req.setUserId(user.getUserId());
            _req.setFirstName(user.getFirstName());
            _req.setLastName(user.getLastName());
            _req.setPassword(user.getPassword());
            _req.setRoles(user.getRoles());
            _req.setChannelCode(user.getChannelCode());

            Pattern pattern = Pattern.compile("^(.+)@(.+)$");

            if (user.getEmail() != null)
                user.setEmail(user.getEmail().toLowerCase());

            if (!pattern.matcher(user.getEmail()).matches())
                throw new EtAuthException("Invalid email format");
            Integer count = userRepo.countByEmail(user.getEmail());
            if (count > 0)
                throw new EtAuthException("Email already in use");

            userRepo.saveAndFlush(_req);

            User userBase = userRepo.findByUserId(_req.getUserId());
            res.setEmail(userBase.getEmail());
            res.setUserId(userBase.getUserId());
            res.setFirstName(userBase.getFirstName());
            res.setLastName(userBase.getLastName());
            res.setPassword(userBase.getPassword());
            res.setRoles(userBase.getRoles());
            res.setChannelCode(userBase.getChannelCode());
            return res;
        } catch (Exception ex) {
            throw new EtInternalServerErrorException(ex.getMessage());
        }
    }

    @Override
    public UserRes findUserById(Integer id) throws EtResourceNotFoundException {
        try {
            User user = userRepo.findByUserId(id);
            UserRes userRes = new UserRes();
            if(user != null){
                userRes.setEmail(user.getEmail());
                userRes.setUserId(user.getUserId());
                userRes.setFirstName(user.getFirstName());
                userRes.setLastName(user.getLastName());
                userRes.setRoles(user.getRoles());
                userRes.setChannelCode(user.getChannelCode());
                return userRes;
            }else{
                throw new EtResourceNotFoundException("No record.");
            }
        } catch (Exception ex) {
            throw new EtResourceNotFoundException(ex.getMessage());
        }
    }

    @Override
    public UserRes findUser(String userName) throws EtResourceNotFoundException {
        try {
            User user = userRepo.findByEmail(userName);
            var userRes = new UserRes();
            if(user != null){
                userRes.setEmail(user.getEmail());
                userRes.setUserId(user.getUserId());
                userRes.setFirstName(user.getFirstName());
                userRes.setLastName(user.getLastName());
                userRes.setRoles(user.getRoles());
                userRes.setChannelCode(user.getChannelCode());
                return userRes;
            }else{
                throw new EtResourceNotFoundException("No record.");
            }
        } catch (Exception ex) {
            throw new EtResourceNotFoundException(ex.getMessage());
        }
    }
}

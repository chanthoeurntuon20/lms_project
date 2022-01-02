package com.amk.lms.services;

import com.amk.lms.exceptions.EtAuthException;
import com.amk.lms.exceptions.EtInternalServerErrorException;
import com.amk.lms.exceptions.EtResourceNotFoundException;
import com.amk.lms.models.req.users.UserReq;
import com.amk.lms.models.res.users.UserRes;

import java.util.List;

public interface UserService {
    UserRes validateUser(String username, String password) throws EtAuthException;
    List<UserRes> findAll() throws EtResourceNotFoundException;
    UserRes addUser(UserReq user) throws  EtInternalServerErrorException;
    UserRes findUserById(Integer id) throws EtResourceNotFoundException;
    UserRes findUser(String userName) throws  EtResourceNotFoundException;
}

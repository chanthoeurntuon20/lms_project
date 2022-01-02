package com.amk.lms.services;

import com.amk.lms.exceptions.EtAuthException;
import com.amk.lms.exceptions.EtInternalServerErrorException;
import com.amk.lms.exceptions.EtResourceNotFoundException;
import com.amk.lms.models.req.customers.AccountReq;
import com.amk.lms.models.req.customers.CustomerReq;
import com.amk.lms.models.res.customers.AccountRes;
import com.amk.lms.models.res.customers.CustomerRes;

import java.util.List;

public interface CustomerService {
    List<CustomerRes> findAll() throws EtInternalServerErrorException;
    CustomerRes addCustomer(CustomerReq req) throws EtInternalServerErrorException;
    CustomerRes findCustomerById(Integer id) throws EtInternalServerErrorException;
    AccountRes findAccountByAcctNo(String acctNo) throws EtResourceNotFoundException;
    void addCustomerAccount(CustomerReq req);
}

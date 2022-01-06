package com.amk.lms.controllers;

import com.amk.lms.exceptions.EtAuthException;
import com.amk.lms.exceptions.EtInternalServerErrorException;
import com.amk.lms.exceptions.EtResourceNotFoundException;
import com.amk.lms.models.req.customers.CustomerReq;
import com.amk.lms.models.req.customers.FilterAccountReq;
import com.amk.lms.models.res.customers.AccountRes;
import com.amk.lms.models.res.customers.CustomerRes;
import com.amk.lms.services.impl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("customer/v1/")
public class CustomerController {
    @Autowired
    private CustomerServiceImpl customerService;

    @PostMapping("create")
    public ResponseEntity<CustomerRes> AddCustomer(@Valid @RequestBody CustomerReq req) throws EtAuthException {
        try{
            CustomerRes customer  = customerService.addCustomer(req);

            req.setId(customer.getId());
            customerService.addCustomerAccount(req);

            customer = customerService.findCustomerById(customer.getId());

            return new ResponseEntity<CustomerRes>(customer,HttpStatus.OK);
        }catch (Exception ex){
            throw new EtInternalServerErrorException(ex.getMessage().toString());
        }
    }

    @GetMapping("customers")
    public ResponseEntity<List<CustomerRes>> GetCustomerList() throws EtAuthException{
        try{
            List<CustomerRes> resList = customerService.findAll();
            return new ResponseEntity<List<CustomerRes>>(resList,HttpStatus.OK);

        }catch (Exception ex){
            throw new EtResourceNotFoundException(ex.getMessage().toString());
        }
    }

    @GetMapping("customers/{id}")
    public ResponseEntity<CustomerRes> GetCustomerById(@PathVariable("id") Integer id) throws EtAuthException{
        try{
            CustomerRes res = customerService.findCustomerById(id);
            return new ResponseEntity<CustomerRes>(res,HttpStatus.OK);

        }catch (Exception ex){
            throw new EtResourceNotFoundException(ex.getMessage().toString());
        }
    }
    @PostMapping("customers/account")
    public ResponseEntity<AccountRes> GetCustomerByAccNo(@RequestBody FilterAccountReq req) throws EtAuthException{
        try{
            AccountRes res = customerService.findAccountByAcctNo(req.getAcctNo());
            return new ResponseEntity<AccountRes>(res,HttpStatus.OK);

        }catch (Exception ex){
            throw new EtResourceNotFoundException(ex.getMessage().toString());
        }
    }
}


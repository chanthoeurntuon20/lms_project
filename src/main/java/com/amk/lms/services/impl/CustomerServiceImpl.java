package com.amk.lms.services.impl;

import com.amk.lms.Repositories.AccountRepository;
import com.amk.lms.Repositories.CustomerRepository;
import com.amk.lms.exceptions.EtAuthException;
import com.amk.lms.exceptions.EtInternalServerErrorException;
import com.amk.lms.exceptions.EtResourceNotFoundException;
import com.amk.lms.models.entities.Account;
import com.amk.lms.models.entities.Customer;
import com.amk.lms.models.req.customers.CustomerReq;
import com.amk.lms.models.res.customers.AccountRes;
import com.amk.lms.models.res.customers.CustomerRes;
import com.amk.lms.services.CustomerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<CustomerRes> findAll() throws EtInternalServerErrorException {
        try{
            List<CustomerRes> customerResList=new ArrayList<>();
            List<Customer> customerList = this.customerRepository.findAll();
            for (int i=0;i<=customerList.size()-1;i++){
                CustomerRes custRes = new CustomerRes();
                custRes.setId(customerList.get(i).getId());
                custRes.setName(customerList.get(i).getName());
                custRes.setGender(customerList.get(i).getGender());
                custRes.setEmail(customerList.get(i).getEmail());
                custRes.setAddress(customerList.get(i).getAddress());
                custRes.setPhoneNumber(customerList.get(i).getPhoneNumber());
                custRes.setMobileNumber(customerList.get(i).getMobileNumber());
                if(customerList.get(i).getAccountList().size()>0){
                    List<AccountRes> accountResList = new ArrayList<>();
                    for (int j=0;j<=customerList.get(i).getAccountList().size()-1;j++){
                        AccountRes accountRes = new AccountRes();
                        accountRes.setAcctNo(customerList.get(i).getAccountList().get(j).getAcctNo());
                        accountRes.setAcctCcy(customerList.get(i).getAccountList().get(j).getAcctCcy());
                        accountRes.setAmount(customerList.get(i).getAccountList().get(j).getAmount());
                        accountRes.setId(customerList.get(i).getAccountList().get(j).getId());
                        accountResList.add(accountRes);
                    }
                    custRes.setAccountResList(accountResList);
                }
                customerResList.add(custRes);
            }
            return customerResList;
        }catch (Exception ex){
            throw new EtAuthException(ex.getMessage());
        }
    }

    @Override
    public CustomerRes addCustomer(CustomerReq req) throws EtInternalServerErrorException {
        try{
            Customer customer =new Customer();

            customer.setName(req.getName());
            customer.setGender(req.getGender());
            customer.setEmail(req.getEmail());
            customer.setAddress(req.getAddress());
            customer.setPhoneNumber(req.getPhoneNumber());
            customer.setMobileNumber(req.getMobileNumber());
            customer.setStatus(req.getStatus());
            Pattern pattern = Pattern.compile("^(.+)@(.+)$");
            if (req.getEmail() != null)
                req.setEmail(req.getEmail().toLowerCase());

            if (!pattern.matcher(req.getEmail()).matches())
                throw new EtAuthException("Invalid email format");

            this.customerRepository.saveAndFlush(customer);
            customer = this.customerRepository.getById(customer.getId());
            var acctList = this.accountRepository.findByCusId(customer.getId());
                List<Account> accountResList = new ArrayList<>();
                for (int j=0;j<=acctList.size()-1;j++){
                    Account accountRes = new Account();
                    accountRes.setAcctNo(acctList.get(j).getAcctNo());
                    accountRes.setAcctCcy(acctList.get(j).getAcctCcy());
                    accountRes.setAmount(acctList.get(j).getAmount());
                    accountRes.setId(acctList.get(j).getId());
                    accountResList.add(accountRes);
                }
            customer.setAccountList(accountResList);

            return GetCustomer(customer);
        }catch (Exception ex){
            throw new EtAuthException(ex.getMessage());
        }
    }

    @Override
    public CustomerRes findCustomerById(Integer id) throws EtInternalServerErrorException {
       try{
           Customer customer = this.customerRepository.getById(id);
           customer.setAccountList(this.accountRepository.findByCusId(id));
           List<Account> accountResList = new ArrayList<>();

           for (int j=0;j<=customer.getAccountList().size()-1;j++){
               Account account = new Account();
               account.setAcctNo(customer.getAccountList().get(j).getAcctNo());
               account.setAcctCcy(customer.getAccountList().get(j).getAcctCcy());
               account.setAmount(customer.getAccountList().get(j).getAmount());
               account.setId(customer.getAccountList().get(j).getId());
               accountResList.add(account);
           }
           customer.setAccountList(accountResList);

           return GetCustomer(customer);
       }catch (Exception ex){
           throw new EtAuthException(ex.getMessage());
       }
    }

    @Override
    public AccountRes findAccountByAcctNo(String acctNo) throws EtResourceNotFoundException {
        try{
            AccountRes accountRes = new AccountRes();
            CustomerRes customerRes =new CustomerRes();
            Account acct = accountRepository.findByAcctNo(acctNo);
            Optional<Customer> customer = customerRepository.findById(acct.getId());
            accountRes.setAcctNo(acct.getAcctNo());
            accountRes.setAcctCcy(acct.getAcctCcy());
            accountRes.setAmount(acct.getAmount());
            accountRes.setId(acct.getId());

            customerRes.setId(customer.get().getId());
            customerRes.setName(customer.get().getName());
            customerRes.setGender(customer.get().getGender());
            customerRes.setEmail(customer.get().getEmail());
            customerRes.setAddress(customer.get().getAddress());
            customerRes.setPhoneNumber(customer.get().getPhoneNumber());
            customerRes.setMobileNumber(customer.get().getMobileNumber());

            accountRes.setCustomerRes(customerRes);
            return accountRes;
        }catch (Exception ex){
            throw new EtResourceNotFoundException(ex.getMessage());
        }
    }

    @Override
    public void addCustomerAccount(CustomerReq req) {
        try{
            //List<Account> accountList=new ArrayList<>();
            for (int i=0;i<=req.getAccountReqList().size()-1;i++) {
                Account acct =new Account();
                acct.setAcctNo(req.getAccountReqList().get(i).getAcctNo());
                acct.setAcctCcy(req.getAccountReqList().get(i).getAcctCcy());
                acct.setAcctName(req.getName());
                acct.setAmount(req.getAccountReqList().get(i).getAmount());
                acct.setCusId(req.getId());
                acct.setCreatedAt(req.getAccountReqList().get(i).getCreatedAt());
                acct.setCreatedBy(req.getAccountReqList().get(i).getCreatedBy());
                acct.setStatus(req.getAccountReqList().get(i).getStatus());

                accountRepository.saveAndFlush(acct);

                //accountList.add(acct);
            }
            //accountRepository.saveAllAndFlush(accountList);
        }catch (Exception ex){
            throw new EtAuthException(ex.getMessage());
        }
    }

    private  CustomerRes GetCustomer(Customer customer){
        List<AccountRes> accountResList=new ArrayList<>();
        CustomerRes customerRes=new CustomerRes();

        customerRes.setId(customer.getId());
        customerRes.setName(customer.getName());
        customerRes.setGender(customer.getGender());
        customerRes.setEmail(customer.getEmail());
        customerRes.setAddress(customer.getAddress());
        customerRes.setPhoneNumber(customer.getPhoneNumber());
        customerRes.setMobileNumber(customer.getMobileNumber());
        for (int i=0;i<=customer.getAccountList().size()-1;i++) {
            AccountRes acct =new AccountRes();
            acct.setAcctNo(customer.getAccountList().get(i).getAcctNo());
            acct.setAcctCcy(customer.getAccountList().get(i).getAcctCcy());
            acct.setAmount(customer.getAccountList().get(i).getAmount());
            acct.setId(customer.getAccountList().get(i).getId());
            accountResList.add(acct);
        }
        customerRes.setAccountResList(accountResList);
        return  customerRes;
    }
}

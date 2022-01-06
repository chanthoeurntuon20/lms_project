package com.amk.lms.models.req.customers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Required;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class CustomerReq {
    @JsonIgnore
    private Integer id;
    @NotNull()
    @NotEmpty(message = "The name is required.")
    @Size(min= 2,max=100,message="he length of full name must be between 2 and 100 characters.")
    private String name;
    @NotEmpty(message = "The gender is required.")
    private String gender;
    @NotEmpty(message = "The email address is required.")
    @Email(message = "The email address is invalid.", flags = { Pattern.Flag.CASE_INSENSITIVE })
    private String email;
    @NotNull()
    private String phoneNumber;
    private String mobileNumber;
    private String address;
    private String status;
    private List<AccountReq> accountReqList;
}

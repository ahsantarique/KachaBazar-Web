package com.kachabazar.customer;


import com.kachabazar.user.UserRepository;
import com.kachabazar.user.WebUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;

@Controller
public class CustomerController {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/join-as-customer")
    public String createCustomerForm(Model model)
    {

        model.addAttribute("customer",new Customer());
        model.addAttribute("user",new WebUser());
        return "create-customer";
    }

    @PostMapping("/join-as-customer")
    public String createCustomerSubmit(@ModelAttribute Customer customer, @ModelAttribute WebUser user, @RequestParam(value="confirmPassword", required=true) String confirmPassword )
    {

        System.out.println(customer.toString());
        System.out.println(user.toString());
        if(!user.getPassword().equals(confirmPassword)) //password mismatch
        {
            return "create-customer";
        }
        if(userRepository.findByUsername(user.getUsername())!=null) //email exists
        {
            return "create-customer";
        }
        user.setRoles(Arrays.asList("customer"));

        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        WebUser newUser = userRepository.save(user);

        customer.setUserId(newUser.getId());
        customerRepository.save(customer);
        return "index";


    }

    @GetMapping("/restaurants")
    public  String restaurants()
    {
        return "customer/restaurants";
    }

}

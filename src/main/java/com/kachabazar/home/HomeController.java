package com.kachabazar.home;




import com.kachabazar.customer.Customer;
import com.kachabazar.customer.CustomerRepository;
import com.kachabazar.shop.Shop;
import com.kachabazar.user.UserRepository;
import com.kachabazar.user.WebUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String test()
    {
        return "index";
    }


    @GetMapping("/login")
    public String login()
    {
        return "login";
    }

    @GetMapping("/home")
    public String home()
    {
        WebUser webUser = (WebUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(webUser.hasRole("shopkeeper"))
        {
            return "redirect:shop";
        }


        return "redirect:/";


    }

    @GetMapping("/register")
    public String signupForm(Model model)
    {

        model.addAttribute("user", new WebUser());
        model.addAttribute("customer", new Customer());
        model.addAttribute("shop", new Shop());

        return "register";
    }












}

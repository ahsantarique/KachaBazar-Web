package com.kachabazar.shop;


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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Controller
public class ShopController {
    @Autowired
    ShopRepository shopRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;



    @GetMapping("/open-your-shop")
    public String createShopForm(Model model)
    {

        model.addAttribute("shop",new Shop());
        model.addAttribute("user",new WebUser());
        return "create-shop";
    }

    @PostMapping("/open-your-shop")
    public String createShopSubmit(@ModelAttribute Shop shop, @ModelAttribute WebUser user, @RequestParam(value="confirmPassword", required=true) String confirmPassword , HttpServletRequest request, HttpServletResponse response)
    {


        if(!user.getPassword().equals(confirmPassword)) //password mismatch
        {
            return "register";
        }
        if(userRepository.findByUsername(user.getUsername())!=null) //email exists
        {
            return "register";
        }
        user.setRoles(Arrays.asList("shopkeeper"));
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        WebUser newUser = userRepository.save(user);

        shop.setUserId(newUser.getId());
        shopRepository.save(shop);
        System.out.println(shop.toString());
        System.out.println(user.toString());
       // authenticateUserAndSetSession(newUser, request);
       // System.out.println("authenticated");
        return "redirect:login";


    }

    @GetMapping("shop")
    public String shop()
    {
        return "theme/admin_2/shop-settings";
    }


}

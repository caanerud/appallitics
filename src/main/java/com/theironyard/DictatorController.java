package com.theironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * Created by chrisaanerud on 4/28/17.
 */
@Controller
public class DictatorController {
    @Autowired
    DictatorRepository dictatorRepository;
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @PostMapping("/loginVerify")
    public String login(String userName, String password, HttpSession session, Model model) throws PasswordStorage.InvalidHashException, PasswordStorage.CannotPerformOperationException {

        User user = dictatorRepository.getByUserName(userName);

        if(user != null && PasswordStorage.verifyPassword(password, user.getPassword())){
            session.setAttribute("userId", user.getId());
            return "redirect:/profile";
        } else {
            model.addAttribute("loginFailed", true);
            return "login";
        }

    }
    @PostMapping(value = "/createAccount")
    public String register(User user) throws PasswordStorage.CannotPerformOperationException {
        user.setPassword(PasswordStorage.createHash(user.getPassword()));

        dictatorRepository.save(user);

        return "redirect:/createform";
    }
}

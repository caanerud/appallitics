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

    // homepage
    @GetMapping("/")
    public String homepage(){
        return "index";
    }

    // login/register screen
    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping("/createform")
    public String createform(){
        return "createform";
    }

    @PostMapping("/loginVerify")
    public String login(String username, String password, HttpSession session, Model model) throws PasswordStorage.InvalidHashException, PasswordStorage.CannotPerformOperationException {


        // gets a particular user by username
        User user = dictatorRepository.getByUserName(username);

        // checks the user inputs, if correct then goes to their profile, it not back to login
        if(user != null && PasswordStorage.verifyPassword(password, user.getPassword())){
            session.setAttribute("userId", user.getId());
            return "redirect:/profile";
        } else {
            model.addAttribute("loginFailed", true);
            return "redirect:/login";
        }

    }
    @PostMapping(value = "/createAccount")
    public String register(User user) throws PasswordStorage.CannotPerformOperationException {
        // sets the password
        user.setPassword(PasswordStorage.createHash(user.getPassword()));

        // puts it in the database
        dictatorRepository.save(user);

        return "redirect:/createform";
    }

    // the about page
    @GetMapping("/about")
    public String about(){
        return "about";
    }

    // leaderboard page
    @GetMapping("/leaderboard")
    public String leaderboard(){
        return "leaderboard";
    }

    // view a profile
    @GetMapping("/profile")
    public String profile(){
        return "profile";
    }

    // voting part
    @GetMapping("/vote")
    public String vote(){
        return "vote";
    }
}

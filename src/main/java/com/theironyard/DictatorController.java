package com.theironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

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


    // LOGIN RELATED !!
    // login/register screen
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // creating dictator form
    @GetMapping("/createform")
    public String createform(){
        return "createform";
    }

    // checking login inputs
    @PostMapping("/loginVerify")
    public String login(String username, String password, HttpSession session, Model model) throws PasswordStorage.InvalidHashException, PasswordStorage.CannotPerformOperationException {

        // Creates list of usernames
        ArrayList<String> usernameList = new ArrayList<>();
        for (int x = 0; x < dictatorRepository.listUsers().size(); x =x + 1){
            usernameList.add(dictatorRepository.listUsers().get(x).getUsername());
        }

        // Checks to see if username is in database
        if (usernameList.contains(username)){
            // gets a particular user by username
            User user = dictatorRepository.getByUserName(username);

            // checks the user inputs, if correct then goes to their profile, it not back to login
            if(user != null && PasswordStorage.verifyPassword(password, user.getPassword())){
                session.setAttribute("userId", user.getId());
                return "redirect:/profile";
            } else {
                // the indication that login failed, stored as a variable
                    model.addAttribute("loginFailed", "Error: Incorrect");
                return "redirect:/login";
            }
        }
        // username not in list
        return "redirect:/login";
    }
    @PostMapping(value = "/createAccount")
    public String register(Model model, String username, String password, String confirm, String email) throws PasswordStorage.CannotPerformOperationException {

        // Creates list of usernames
        ArrayList<String> usernameList = new ArrayList<>();
        for (int x = 0; x < dictatorRepository.listUsers().size(); x =x + 1){
            usernameList.add(dictatorRepository.listUsers().get(x).getUsername());
        }

        if (!usernameList.contains(username) && password.equals(confirm)){
            User user = new User(username,password,email);
            // sets the password
            user.setPassword(PasswordStorage.createHash(user.getPassword()));

            // puts it in the database
            dictatorRepository.save(user);

            // redirect to creating dictator
            return "redirect:/createform";
        }
        // failed to create account
        model.addAttribute("loginFailed", true);

        // redirect to login
        return "redirect:/login";
    }

    // CREATING DICTATOR, EDITING DICTATOR, AND VIEWING DICTATOR !!
    //creation and edit
    @PostMapping("/createDictator")
    public String createDictator(Model model, HttpSession session, @RequestParam(name = "file") MultipartFile file, String overviewBlurb,
                                 String overviewDictatorshipName, String favcolor, String mascot, String econLabor,
                                 String econTax, String econTrade, String econInfrastructure, String econMilitary,
                                 String socialHealthcare, String socialRetirement, String socialEducation,
                                 String socialEnvironment, String socialWelfare, String legalPunishment,
                                 String legalImmigration, String legalVotingRights, String legalPrivacyLaws,
                                 String legalWeapons) throws IOException {

        // Getting id from session
        Integer userId = (Integer) session.getAttribute("userId");

        // creating the dictator
        Dictator dictator = new Dictator(new User(userId), overviewBlurb, overviewDictatorshipName,
                mascot, favcolor, file.getContentType(), file.getBytes(), econLabor, econTax, econTrade,
                econInfrastructure,econMilitary,socialHealthcare,socialRetirement,socialEducation,
                socialEnvironment,socialWelfare,legalPunishment,legalImmigration,legalVotingRights,
                legalPrivacyLaws,legalWeapons,0,0);

        // saving the dictator to database
        dictatorRepository.saveDictator(dictator);

        return "redirect:/profile";
    }

    // view a profile/Dictator
    @GetMapping("/profile")
    public String profile(Model model, HttpSession session){
        // Getting id from session
        Integer userId = (Integer) session.getAttribute("userId");

        // Getting dictator from session id
        Dictator dictator = dictatorRepository.getDictatorById(userId);

        // Adding the dictator to the model
        model.addAttribute("dictator",dictator);

        return "profile";
    }


    // OTHERS !!

    // the about page
    @GetMapping("/about")
    public String about(){
        return "about";
    }

    // leaderboard page
    @GetMapping("/leaderboard")
    public String leaderboard(Model model, String search){
        ArrayList<Integer> rank = new ArrayList<>();
        for (int x = 1; x <= dictatorRepository.listUsers().size(); x = x + 1){
            rank.add(x);
        }
        model.addAttribute("rank",rank);
        model.addAttribute("bestDictators",dictatorRepository.listBestDictators(search));
        return "leaderboard";
    }

    // voting part
    @GetMapping("/vote")
    public String vote(){
        return "vote";
    }
}

package com.theironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    public String login(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null){
            // not logged in
            userId = 0;
        }

        return "login";
    }

    // creating dictator form
    @GetMapping("/createform")
    public String createform(Model model, HttpSession session){
        // Getting id from session
        Integer userId = (Integer) session.getAttribute("userId");

        // modeling empty dictator
        model.addAttribute("dictator",dictatorRepository.getDictatorById(userId));

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
                    model.addAttribute("loginFailed", "Error: Incorrect Password!");
                return "redirect:/login";
            }
        }

        // username not in list, error then redirect
        model.addAttribute("badUsername","Error: Bad Username!");

        return "redirect:/login";
    }
    @PostMapping(value = "/createAccount")
    public String register(HttpSession session, Model model, String username, String password, String confirm, String email) throws PasswordStorage.CannotPerformOperationException {

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

            // gets the same user by username, with the id from the database
            User userWithId = dictatorRepository.getByUserName(username);

            // sets a session id
            session.setAttribute("userId", userWithId.getId());

            // creates blank dictator
            Dictator dictator = new Dictator(new User(userWithId.getId()),
                    " ", " "," ","#ffffff"," ",new byte [1],
                    " "," "," "," "," ",
                    " "," "," "," "," ",
                    " "," "," "," "," ",
                    0, 0);
            dictatorRepository.saveDictator(dictator);

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
        dictatorRepository.updateDictator(dictator);

        return "redirect:/profile";
    }

    @GetMapping("/editDictator")
    public String editDictator(HttpSession session, Model model){
        // Gets the id of the user
        Integer userId = (Integer) session.getAttribute("userId");

        // Gets the dictator so that the input fields may be filled with previous answers
        model.addAttribute("dictator",dictatorRepository.getDictatorById(userId));

        // Checker for mascot (select, option)


        return "createform";
    }

    // view a profile/Dictator
    @GetMapping("/profile")
    public String profile(Model model, HttpSession session, Integer dictatorId){
        // Getting id from session
        Integer userId = (Integer) session.getAttribute("userId");

        // variable dictator
        Dictator dictator = new Dictator();

        // Getting dictator from session id or dictatorId
        if (dictatorId == null) {
            dictator = dictatorRepository.getDictatorById(userId);
        } else {
            dictator = dictatorRepository.getDictatorById(dictatorId);
        }

        // Adding the dictator to the model
        model.addAttribute("dictator",dictator);

        // Creating variable to see if viewer is owner
        Boolean owner = true;
        if (userId != dictatorId){
            owner = false;
        }
        // Handling case when viewerId is null (when user just login to see their own page)
        if (dictatorId == null){
            owner = true;
        }
        model.addAttribute("owner",owner);

        return "profile";
    }


    // OTHERS !!

    // the about page
    @GetMapping("/about")
    public String about(){
        return "about";
    }

    // leaderboard page, sort by best (most pledges)
    @GetMapping("/leaderboard")
    public String leaderboard(Model model, @RequestParam(defaultValue = "") String search){

        model.addAttribute("dictators",dictatorRepository.listBestDictators(search));

        return "leaderboard";
    }

    // leaderboard sort worst
    @GetMapping("/worstDictators")
    public String worstDictators(Model model, @RequestParam(defaultValue = "") String search){

        model.addAttribute("dictators", dictatorRepository.listWorstDictators(search));

        return "leaderboard";
    }

    // leaderboard sort by score
    @GetMapping("/score")
    public String score(Model model, @RequestParam(defaultValue = "") String search){
        model.addAttribute("dictators",dictatorRepository.sortByScore(search));
        return "leaderboard";
    }

    // voting part
    @GetMapping("/vote")
    public String vote(Model model, HttpSession session){
        // The user id of the voter
        Integer userId = (Integer) session.getAttribute("userId");

        // List of users, going to get their ids for dictator
        List<User> listOfUsers = dictatorRepository.listUsers();

        // Creating array of ids, subtracting 1 for the voter (not yet populated)
        int[] x = new int[listOfUsers.size() - 1];

        // Removing voter from the list of users
         for (int y = 0; y < listOfUsers.size(); y = y + 1){
             if (userId == listOfUsers.get(y).getId()){
                 listOfUsers.remove(y);
             };
         }

         // adding the remaining list to the integer array
        for (int z = 0; z < listOfUsers.size(); z= z + 1){
             x[z]=listOfUsers.get(z).getId();
        }

         // Random number generator that will link to a dictator
        Random random = new Random();
        int number = random.nextInt(listOfUsers.size());

        // The lucky dictator to get a vote
        Dictator luckyDictator = dictatorRepository.getDictatorById(x[number]);

        // Modeling the lucky dictator
        model.addAttribute("dictator",luckyDictator);

        return "vote";
    }

    // pledge and vote from vote page
    @GetMapping("/pledge")
    public String pledge(Integer dictatorId){
        // getting the dictator
        Dictator dictator = dictatorRepository.getDictatorById(dictatorId);

        // updating the pledge value by 1, storing as integer pledge
        Integer pledge = dictator.getPledge()+1;

        // sending it back to database
        dictatorRepository.pledge(pledge,dictatorId);

        return "redirect:/vote";
    }

    @GetMapping("/revolt")
    public String revolt(Integer dictatorId){
        // getting the dictator
        Dictator dictator = dictatorRepository.getDictatorById(dictatorId);

        // updating the revolt value by 1, storing as integer revolt
        Integer revolt = dictator.getRevolt()+1;

        // sending it back to database
        dictatorRepository.revolt(revolt,dictatorId);

        return "redirect:/vote";
    }

    // from profile page
    @GetMapping("/pledgeProfile")
    public String pledgeProfile(Integer dictatorId){
        // getting the dictator
        Dictator dictator = dictatorRepository.getDictatorById(dictatorId);

        // updating the pledge value by 1, storing as integer pledge
        Integer pledge = dictator.getPledge()+1;

        // sending it back to database
        dictatorRepository.pledge(pledge,dictatorId);

        return "redirect:/profile?dictatorId="+dictatorId;
    }

    @GetMapping("/revoltProfile")
    public String revoltFrom(Integer dictatorId){
        // getting the dictator
        Dictator dictator = dictatorRepository.getDictatorById(dictatorId);

        // updating the pledge value by 1, storing as integer pledge
        Integer revolt = dictator.getRevolt()+1;

        // sending it back to database
        dictatorRepository.revolt(revolt,dictatorId);

        return "redirect:/profile?dictatorId="+dictatorId;
    }


    // Getting the picture
    @GetMapping("/dictator/image")
    @ResponseBody
    public ResponseEntity serveFile(Integer id) throws URISyntaxException {

        Dictator dictator = dictatorRepository.getDictatorById(id);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, dictator.getOverviewContentType())
                .body(dictator.getOverviewImage());

    }

    // logout
    @RequestMapping(path = "/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
}

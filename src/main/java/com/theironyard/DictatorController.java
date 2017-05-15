package com.theironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
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
    public String homepage(Model model, HttpSession session){
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null){
            // not logged in
            userId = 0;
        }
        model.addAttribute("checkloggedin", userId);
        return "index";
    }


    // LOGIN RELATED !!
    // login/register screen
    @GetMapping("/login")
    public String login(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null){
            // not logged in
            userId = 0;
        }
        model.addAttribute("checkloggedin", userId);

        if (userId != 0) {
            return "redirect:/profile";
        }

        return "login";
    }

    // creating dictator form
    @GetMapping("/createform")
    public String createform(Model model, HttpSession session){
        // Getting id from session
        Integer userId = (Integer) session.getAttribute("userId");
        Dictator dictator = dictatorRepository.getDictatorById(userId);

        // modeling dictator
        model.addAttribute("dictator",dictator);

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
                model.addAttribute("error", "Incorrect Password!");
                model.addAttribute("errorTrue",true);
                model.addAttribute("checkloggedin", 0);
                return "login";
            }
        }

        // username not in list, error then redirect
        model.addAttribute("error","Error: Username does not exist!");
        model.addAttribute("errorTrue",true);
        model.addAttribute("checkloggedin", 0);

        return "login";
    }

    @PostMapping(value = "/createAccount")
    public String register(HttpSession session, Model model, String username, String password, String confirm, String email) throws PasswordStorage.CannotPerformOperationException {

        // Creates list of usernames & a list of emails
        ArrayList<String> usernameList = new ArrayList<>();
        ArrayList<String> emailList = new ArrayList<>();
        for (int x = 0; x < dictatorRepository.listUsers().size(); x =x + 1){
            usernameList.add(dictatorRepository.listUsers().get(x).getUsername());
            emailList.add(dictatorRepository.listUsers().get(x).getEmail());
        }

        // Checks for uniqueness of email
        if (emailList.contains(email)){
            model.addAttribute("error","Email has been used!");
            model.addAttribute("errorTrue",true);

            return "login";
        }
        // Checks for uniqueness of username.
        if (usernameList.contains(username)){
            model.addAttribute("error","Username has been taken!");
            model.addAttribute("errorTrue",true);

            return "login";
        }
        // Checks if password matches
        if (!password.equals(confirm)){
            model.addAttribute("error","Passwords did not match!");
            model.addAttribute("errorTrue",true);

            return "login";
        }

        // creates the user
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

//    // forgot password
//    @GetMapping("/password")
//    public String password(){
//        return "password";
//    }
//
//    @PostMapping("/forgetPassword")
//    public String forgetPassword(String username, String email){
//
//        // Getting the list of users and storing it as a variable
//        List<User> listOfUsers = dictatorRepository.listUsers();
//
//        // The email
//        Email sendEmail = new Email();
//
//        // The forgetful one, send email to him
//        for (int x = 0; x < listOfUsers.size(); x = x + 1){
//            if (listOfUsers.get(x).getUsername() == username && listOfUsers.get(x).getEmail() == email){
//                sendEmail.email("email","Reset Password",
//                        "Click on this link, https://appallitics.herokuapp.com/"+listOfUsers.get(x).getPassword()+", to reset password.");
//            }
//        }
//
//        return "password";
//    }



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

    // From dictator to minion
    @GetMapping("/minion")
    public String minion(HttpSession session) {
        // Gets the id of the user
        Integer userId = (Integer) session.getAttribute("userId");
        Dictator dictator = new Dictator(new User(userId), " ", " ",
                " ", " ", " ", new byte[]{}, " ", " ", " ",
                " "," "," "," "," ",
                " "," "," "," "," ",
                " "," ",0,0);

        // saving the minion to database
        dictatorRepository.updateDictator(dictator);

        return "redirect:/profile";
    }

    @GetMapping("/editDictator")
    public String editDictator(HttpSession session, Model model){
        // Gets the id of the user
        Integer userId = (Integer) session.getAttribute("userId");
        Dictator dictator = dictatorRepository.getDictatorById(userId);

        // Gets the dictator so that the input fields may be filled with previous answers
        model.addAttribute("dictator",dictator);

        // PRE-SELECTED VALUES
//        // image
//        if (!dictator.getOverviewContentType().isEmpty()) {
//            dictator.setOverviewImage(dictator.getOverviewImage());
//            dictator.setOverviewContentType(dictator.getOverviewContentType());
//        }

        // color
        if (dictator.getOverviewColor().equals("aliceblue")){
            model.addAttribute("aliceblue",true);
        } else if (dictator.getOverviewColor().equals("aquamarine")){
            model.addAttribute("aquamarine",true);
        } else if (dictator.getOverviewColor().equals("bisque")){
            model.addAttribute("bisque",true);
        } else if (dictator.getOverviewColor().equals("black")){
            model.addAttribute("black",true);
        } else if (dictator.getOverviewColor().equals("blueviolet")){
            model.addAttribute("blueviolet",true);
        } else if (dictator.getOverviewColor().equals("brown")){
            model.addAttribute("brown",true);
        } else if (dictator.getOverviewColor().equals("cadetblue")){
            model.addAttribute("cadetblue",true);
        } else if (dictator.getOverviewColor().equals("coral")){
            model.addAttribute("coral",true);
        } else if (dictator.getOverviewColor().equals("cornflowerblue")){
            model.addAttribute("cornflowerblue",true);
        } else if (dictator.getOverviewColor().equals("crimson")){
            model.addAttribute("crimson",true);
        } else if (dictator.getOverviewColor().equals("darkblue")){
            model.addAttribute("darkblue",true);
        } else if (dictator.getOverviewColor().equals("darkgoldenrod")){
            model.addAttribute("darkgoldenrod",true);
        } else if (dictator.getOverviewColor().equals("darkorange")){
            model.addAttribute("darkorange",true);
        } else if (dictator.getOverviewColor().equals("darksalmon")){
            model.addAttribute("darksalmon",true);
        } else if (dictator.getOverviewColor().equals("yellow")){
            model.addAttribute("yellow",true);
        }

        // mascot
        if (dictator.getOverviewMascot().equals("Lion")){
            model.addAttribute("Lion",true);
        } else if (dictator.getOverviewMascot().equals("Bear")){
            model.addAttribute("Bear",true);
        } else if (dictator.getOverviewMascot().equals("ClownFish")){
            model.addAttribute("ClownFish",true);
        } else if (dictator.getOverviewMascot().equals("Mouse")){
            model.addAttribute("Mouse",true);
        } else if (dictator.getOverviewMascot().equals("Crocodile")){
            model.addAttribute("Crocodile",true);
        } else if (dictator.getOverviewMascot().equals("Parrot")){
            model.addAttribute("Parrot",true);
        } else if (dictator.getOverviewMascot().equals("Rhino")){
            model.addAttribute("Rhino",true);
        } else if (dictator.getOverviewMascot().equals("Shark")){
            model.addAttribute("Shark",true);
        } else if (dictator.getOverviewMascot().equals("Trout")){
            model.addAttribute("Trout",true);
        } else if (dictator.getOverviewMascot().equals("Giraffe")){
            model.addAttribute("Giraffe",true);
        }

        // econ first multiple choice (infrastructure)
        if (dictator.getEconInfrastructure().equals("I will use tax money to sustain our crumbling infrastructure.")){
            model.addAttribute("econInfrastructure1",true);
        } else if (dictator.getEconInfrastructure().equals("I will use tax money to repair and improve our crumbling infrastructure.")){
            model.addAttribute("econInfrastructure2",true);
        } else if (dictator.getEconInfrastructure().equals("I will use paid minion labor to sustain our crumbling infrastructure.")){
            model.addAttribute("econInfrastructure3",true);
        } else if (dictator.getEconInfrastructure().equals("I will use free minion labor to sustain our crumbling infrastructure.")){
            model.addAttribute("econInfrastructure4",true);
        } else if (dictator.getEconInfrastructure().equals("I will ignore our crumbling infrastructure and focus on more important matters.")){
            model.addAttribute("econInfrastructure5",true);
        } else if (dictator.getEconInfrastructure().equals("I will actively destroy our crumbling infrastructure.")){
            model.addAttribute("econInfrastructure6",true);
        }
        // econ second multiple choice (tax)
        if (dictator.getEconTax().equals("Tax 100% of minion income.")){
            model.addAttribute("econTax1",true);
        } else if (dictator.getEconTax().equals("Tax 75% of minion income.")){
            model.addAttribute("econTax2", true);
        } else if (dictator.getEconTax().equals("Tax 50% of minion income.")){
            model.addAttribute("econTax3", true);
        } else if (dictator.getEconTax().equals("Tax 35% of minion income.")){
            model.addAttribute("econTax4", true);
        } else if (dictator.getEconTax().equals("Tax 15% of minion income.")){
            model.addAttribute("econTax5", true);
        } else if (dictator.getEconTax().equals("No tax")){
            model.addAttribute("econTax6", true);
        } else if (dictator.getEconTax().equals("I plan on abolishing currency.")){
            model.addAttribute("econTax7", true);
        }

        // social first multiple choice (environment)
        if (dictator.getSocialEnvironment().equals("I will sustain the current state of our polluted, festering wasteland.")){
            model.addAttribute("socialEnvironment1",true);
        } else if (dictator.getSocialEnvironment().equals("I will work to improve and care for our polluted, festering wasteland.")){
            model.addAttribute("socialEnvironment2",true);
        } else if (dictator.getSocialEnvironment().equals("I will actively sabotage the environment of our polluted, festering wasteland.")){
            model.addAttribute("socialEnvironment3",true);
        } else if (dictator.getSocialEnvironment().equals("I will refuse to acknowledge that we live in a polluted, festering wasteland.")){
            model.addAttribute("socialEnvironment4",true);
        } else if (dictator.getSocialEnvironment().equals("I will actively work on finding a country to invade that isn't a polluted, festering wasteland.")){
            model.addAttribute("socialEnvironment5",true);
        } else if (dictator.getSocialEnvironment().equals("I will encourage space travel in order to find a habitable planet that isn't a polluted, festering wasteland.")){
            model.addAttribute("socialEnvironment6",true);
        }
        // social second multiple choice (retirement)
        if (dictator.getSocialRetirement().equals("All minions work until I decide otherwise.")){
            model.addAttribute("socialRetirement1",true);
        } else if (dictator.getSocialRetirement().equals("There is no retirement. The word 'retirement' ceases to exist effective immediately.")){
            model.addAttribute("socialRetirement2",true);
        } else if (dictator.getSocialRetirement().equals("Retirement age of 65, and then you live on your meager savings.")){
            model.addAttribute("socialRetirement3",true);
        } else if (dictator.getSocialRetirement().equals("All older minions move into retirement homes and then perform menial tasks to further serve the country.")){
            model.addAttribute("socialRetirement4",true);
        } else if (dictator.getSocialRetirement().equals("Minions can retire at a reasonable old age and then mooch off younger minions.")){
            model.addAttribute("socialRetirement5",true);
        }

        // legal first multiple choice (immigration)
        if (dictator.getLegalImmigration().equals("I will encourage minions from other countries to come and do my bidding.")){
            model.addAttribute("legalImmigration1",true);
        } else if (dictator.getLegalImmigration().equals("I will discourage minions from other countries to come and do my bidding.")){
            model.addAttribute("legalImmigration2",true);
        } else if (dictator.getLegalImmigration().equals("I will allow minions from other countries to visit and do my bidding and then let them return home.")){
            model.addAttribute("legalImmigration3",true);
        } else if (dictator.getLegalImmigration().equals("I will actively work to conquer foreign lands so that their minions can do my bidding.")){
            model.addAttribute("legalImmigration4",true);
        } else if (dictator.getLegalImmigration().equals("I will encourage you to move to another country if you aren't a productive and satisfied member of society.")){
            model.addAttribute("legalImmigration5",true);
        }
        // legal second multiple choice (privacy laws)
        if (dictator.getLegalPrivacyLaws().equals("I will use the latest technology to monitor every aspect of your lives and I will be open and honest about it.")){
            model.addAttribute("legalPrivacyLaws1",true);
        } else if (dictator.getLegalPrivacyLaws().equals("I will use the latest technology to monitor every aspect of your lives and I will hide this from you.")){
            model.addAttribute("legalPrivacyLaws2",true);
        } else if (dictator.getLegalPrivacyLaws().equals("I will let you gather and share information with only moderate fear of persecution.")){
            model.addAttribute("legalPrivacyLaws3",true);
        } else if (dictator.getLegalPrivacyLaws().equals("I will let you gather and share information while working to undermine your efforts.")){
            model.addAttribute("legalPrivacyLaws4",true);
        } else if (dictator.getLegalPrivacyLaws().equals("I will let you decide which aspects of your lives that I assert control over.")){
            model.addAttribute("legalPrivacyLaws5",true);
        } else if (dictator.getLegalPrivacyLaws().equals("I will allow you to live your lives free of constant oversight and supervision.")){
            model.addAttribute("legalPrivacyLaws6",true);
        }

        return "createform";
    }

    // view a profile/Dictator
    @GetMapping("/profile")
    public String profile(Model model, HttpSession session, Integer dictatorId, String error, boolean errorExists, String defenderMessage, String attackerMessage){
        // Getting id from session
        Integer userId = (Integer) session.getAttribute("userId");

        // variable dictator
        Dictator dictator;

        // Getting dictator from session id (seeing own profile case) or dictatorId
        if (dictatorId == null) {
            dictator = dictatorRepository.getDictatorById(userId);
        } else {
            dictator = dictatorRepository.getDictatorById(dictatorId);
        }

        // MINION !! IF STATEMENT HERE
        if (dictator.getLegalWeapons().trim().isEmpty()){
            model.addAttribute("minion",true);
        } else {
            model.addAttribute("minion",false);
        }

        // Adding the dictator to the model
        model.addAttribute("dictator",dictator);

        // Twitter blurb
        model.addAttribute("twitterBlurb",dictator.getOverviewBlurb().replaceAll(" ","%20"));

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

        // Checks if user is logged in or not
        if (userId == null){
            // not logged in
            userId = 0;
        }
        model.addAttribute("checkloggedin", userId);

        // Attack error messages
        model.addAttribute("errorExists",errorExists);
        model.addAttribute("error",error);

        // Result of attack
        model.addAttribute("defenderMessage",defenderMessage);
        model.addAttribute("attackerMessage",attackerMessage);

        return "profile";
    }


    // OTHERS !!

    // the about page
    @GetMapping("/about")
    public String about(Model model, HttpSession session){
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null){
            // not logged in
            userId = 0;
        }
        model.addAttribute("checkloggedin", userId);
        return "about";
    }

    // leaderboard page, sort by best (most pledges)
    @GetMapping("/leaderboard")
    public String leaderboard(Model model, HttpSession session, @RequestParam(defaultValue = "") String search){
        // Checking if user is logged in or not
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null){
            // not logged in
            userId = 0;
        }
        model.addAttribute("checkloggedin", userId);

        // The searched list
        List<Dictator> searchDictators = dictatorRepository.listBestDictators(search);

        // Getting the list of best dictators
        List<Dictator> dictatorList = new ArrayList<>();

        // Getting the dictators (with data)
        for (int x = 0; x < searchDictators.size(); x = x + 1){
            if (!searchDictators.get(x).getLegalWeapons().trim().isEmpty()){
                dictatorList.add(searchDictators.get(x));
            }
        }

        // Sending the filtered list of dictators to leaderboard
        model.addAttribute("dictators",dictatorList);

        return "leaderboard";
    }

    // leaderboard sort best
    @GetMapping("/bestDictators")
    public String bestDictators(Model model, @RequestParam(defaultValue = "") String search, Integer firstPosition){
        // The searched list
        List<Dictator> searchDictators = dictatorRepository.listBestDictators(search);

        // sorting the other way if clicked again
        if (searchDictators.get(0).getUser().getId() == firstPosition){
            searchDictators = dictatorRepository.listBestDictatorsSort(search);
        }

        // Getting the list of worst dictators
        List<Dictator> dictatorList = new ArrayList<>();

        // Getting the dictators (with data)
        for (int x = 0; x < searchDictators.size(); x = x + 1){
            if (!searchDictators.get(x).getLegalWeapons().trim().isEmpty()){
                dictatorList.add(searchDictators.get(x));
            }
        }

        // Sending the filtered list of dictators to leaderboard
        model.addAttribute("dictators",dictatorList);

        return "leaderboard";
    }

    // leaderboard sort worst
    @GetMapping("/worstDictators")
    public String worstDictators(Model model, @RequestParam(defaultValue = "") String search, Integer firstPosition){

    // The searched list
        List<Dictator> searchDictators = dictatorRepository.listWorstDictators(search);

        // sorting the other way if clicked again
        if (searchDictators.get(0).getUser().getId() == firstPosition){
            searchDictators = dictatorRepository.listWorstDictatorsSort(search);
        }

        // Getting the list of worst dictators
        List<Dictator> dictatorList = new ArrayList<>();

        // Getting the dictators (with data)
        for (int x = 0; x < searchDictators.size(); x = x + 1){
            if (!searchDictators.get(x).getLegalWeapons().trim().isEmpty()){
                dictatorList.add(searchDictators.get(x));
            }
        }

        // Sending the filtered list of dictators to leaderboard
        model.addAttribute("dictators",dictatorList);

        return "leaderboard";
    }

    // leaderboard sort by score
    @GetMapping("/score")
    public String score(Model model, @RequestParam(defaultValue = "") String search, Integer firstPosition){

        // The searched list
        List<Dictator> searchDictators = dictatorRepository.sortByScore(search);

        // sorting the other way if clicked again
        if (searchDictators.get(0).getUser().getId() == firstPosition){
            searchDictators = dictatorRepository.sortByScoreSort(search);
        }

        // Getting the list of dictators sorted by score
        List<Dictator> dictatorList = new ArrayList<>();

        // Getting the dictators (with data)
        for (int x = 0; x < searchDictators.size(); x = x + 1){
            if (!searchDictators.get(x).getLegalWeapons().trim().isEmpty()){
                dictatorList.add(searchDictators.get(x));
            }
        }

        // Sending the filtered list of dictators to leaderboard
        model.addAttribute("dictators",dictatorList);

        return "leaderboard";
    }

    // voting part
    @GetMapping("/gauntlet")
    public String gauntlet(Model model, HttpSession session, Integer dictatorId){
        // The user id of the voter
        Integer userId = (Integer) session.getAttribute("userId");

        // Everybody
        List<Dictator> allUsers = dictatorRepository.listBestDictators("");

        // Filtering out people without filled out dictator (no minions)
        List<Dictator> dictatorList = new ArrayList<>();

        // Getting the dictators (with data)
        for (int x = 0; x < allUsers.size(); x = x + 1){
            if (!allUsers.get(x).getLegalWeapons().trim().isEmpty()){
                dictatorList.add(allUsers.get(x));
            }
        }

        // first time entering gauntlet page
        if (dictatorId == null){
            dictatorId = -1;
        }

        // Checking if user is a dictator, and removing if he/she is & the previous dictator seen
        if (!dictatorRepository.getDictatorById(userId).getLegalWeapons().trim().isEmpty()) {
            for (int y = 0; y < dictatorList.size(); y = y + 1) {
                if (dictatorList.get(y).getUser().getId() == userId || dictatorList.get(y).getUser().getId() == dictatorId) {
                    dictatorList.remove(y);
                }
            }
        }

        // GETTING RANDOM DICTATOR TO VIEW
        // creating array of ids
        int[] x = new int[dictatorList.size()];
        for (int y = 0; y < dictatorList.size(); y = y + 1){
            x[y] = dictatorList.get(y).getUser().getId();
        }

         // Random number generator that will link to a dictator
        Random random = new Random();
        int number = random.nextInt(dictatorList.size());

        // The lucky dictator to get a vote
        Dictator luckyDictator = dictatorRepository.getDictatorById(x[number]);

        // Modeling the lucky dictator
        model.addAttribute("dictator",luckyDictator);

        if (userId == null){
            // not logged in
            userId = 0;
        }
        model.addAttribute("checkloggedin", userId);

        return "gauntlet";
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

        return "redirect:/gauntlet?dictatorId="+dictatorId;
    }

    @GetMapping("/revolt")
    public String revolt(Integer dictatorId){
        // getting the dictator
        Dictator dictator = dictatorRepository.getDictatorById(dictatorId);

        // updating the revolt value by 1, storing as integer revolt
        Integer revolt = dictator.getRevolt()+1;

        // sending it back to database
        dictatorRepository.revolt(revolt,dictatorId);

        return "redirect:/gauntlet?dictatorId="+dictatorId;
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

    // ATTACK !!
    @GetMapping("/attack")
    public String attack(Model model, HttpSession session, Integer dictatorId){
        // dictator defending
        Dictator dictatorDefend = dictatorRepository.getDictatorById(dictatorId);

        // dictator attacking
        Integer userId = (Integer) session.getAttribute("userId");
        Dictator dictatorAttack = dictatorRepository.getDictatorById(userId);

        // divide by 0 error
        if (dictatorAttack.getPledge() == 0){
            return "redirect:/profile?dictatorId="+dictatorId + "&error=You have no pledged minions to attack!"+ "&errorExists="+true;
        }

        if (dictatorDefend.getPledge() == 0){
            return "redirect:/profile?dictatorId="+dictatorId + "&error=The dictator has nothing to lose!"+ "&errorExists="+true;
        }

        // Logic for attacking/defending, taking into account revolts (and making it a bit more favorable for defender)
        Double effectiveAttack = dictatorAttack.getPledge() * 1.0 / (dictatorAttack.getPledge()+dictatorAttack.getRevolt());
        Double effectiveDefend = (dictatorDefend.getPledge() * 1.1) / (dictatorDefend.getPledge()+dictatorDefend.getRevolt());
        Double overall = effectiveAttack / effectiveDefend;

        // Checking to see which one is the whole number (with maximum limit)
        Integer sacrifice = 1;
        Integer defenderLost = 1;
        if (overall < 1){
            overall = 1 / overall;
            sacrifice = overall.intValue();
            if (sacrifice > 4){
                sacrifice = 4;
            }
            model.addAttribute("sacrifice",sacrifice);
            model.addAttribute("defenderLost",1);
        } else{
            defenderLost = overall.intValue();
            if (defenderLost > 4){
                defenderLost = 4;
            }
            model.addAttribute("sacrifice",1);
            model.addAttribute("defenderLost",defenderLost);
        }

        // No negative pledges
        if (dictatorAttack.getPledge() < sacrifice){
            return "redirect:/profile?dictatorId="+dictatorId + "&error=You have too little loyal minions!" + "&errorExists="+true;
        }
        if (dictatorDefend.getPledge() < defenderLost){
            return "redirect:/profile?dictatorId="+dictatorId + "&error=The dictator you are attacking has too little loyal minions!" + "&errorExists="+true;
        }

        // Attack successful
        dictatorRepository.pledge(dictatorAttack.getPledge()-sacrifice,userId);
        dictatorRepository.pledge(dictatorDefend.getPledge()-defenderLost,dictatorId);

        return "redirect:/profile?dictatorId="+dictatorId + "&attackerMessage=You have lost "+ sacrifice + " pledged minions!" + "&defenderMessage=" + dictatorDefend.getUser().getUsername() + " has lost " + defenderLost + " pledged minions!" + "&errorExists="+false;
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



//    @PostMapping("/pledge-click")
//    @ResponseBody
//    public void pledgeClick(Integer dictatorId, Integer count){
//        dictatorRepository.pledge(dictatorId, count);
//
//    }
//
//    @PostMapping("/revolt-click")
//    @ResponseBody
//    public void revoltClick(Integer dictatorId, Integer count){
//        dictatorRepository.revolt(dictatorId, count);
//
//    }

}


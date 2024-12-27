package com.javaproject.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.javaproject.beans.BoardBase;
import com.javaproject.beans.Review;
import com.javaproject.database.DatabaseAccess;

@Controller
public class HomeController {

    @Autowired
    DatabaseAccess da;

    @Autowired
    @Lazy
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JdbcUserDetailsManager jdbcUserDetailsManager;

    @GetMapping("/newUser")
    public String newUser(Model model) {

        List<String> authorities = da.getAuthorities();
        model.addAttribute("authorities", authorities);
        return "new-user";
    }

    @PostMapping("/addUser")
    public String addUser(@RequestParam String userName, @RequestParam String password,
            @RequestParam String[] authorities, Model model, RedirectAttributes redirectAttrs) {

        List<GrantedAuthority> authorityList = new ArrayList<>();

        for (String authority : authorities) {
            authorityList.add(new SimpleGrantedAuthority(authority));
        }
        String encodedPassword = passwordEncoder.encode(password);

        // check existing user
        if (jdbcUserDetailsManager.userExists(userName)) {
            model.addAttribute("errorMsg", "User name already Exists. Try a different user name.");
            model.addAttribute("authorities", authorityList);
            return "new-user";
        } else {
            User user = new User(userName, encodedPassword, authorityList);

            jdbcUserDetailsManager.createUser(user);
            redirectAttrs.addFlashAttribute("userAddedMsg", "User succesfully added!");
            return "redirect:/";
        }
    }

    @GetMapping("/")
    public String goHome(Model model) {
        List<BoardBase> boardbases = da.getBoardBases();
        model.addAttribute("boardbases", boardbases);
        return "index";
    }

    @GetMapping("/{id}")
    public String getBoardbaseDetail(@PathVariable Long id, Model model) {
        model.addAttribute("boardbase", da.getBoardBase(id));
        return "boardbase";
    }

    @GetMapping("/{id}/reviews")
    public String getReviews(@PathVariable Long id, Model model) {
        model.addAttribute("Boardbase", da.getBoardBase(id));
        model.addAttribute("reviews", da.getReviews(id));
        return "review";
    }

    @GetMapping("/secured/addReview/{id}")
    public String addReview(@PathVariable Long id, Model model) {
        model.addAttribute("boardbase", da.getBoardBase(id));
        model.addAttribute("review", new Review());

        return "secured/addReview";
    }

    // edit the review
    @GetMapping("/{gameId}/reviews/{id}")
    public String editReview(@PathVariable Long gameId, @PathVariable Long id, Model model) {
        Review review = da.getReview(id);
        model.addAttribute("review", review);
        model.addAttribute("boardbase", da.getBoardBase(gameId));
        return "secured/addReview";
    }

    @GetMapping("/secured/addBoardBase")
    public String addBoardBase(Model model) {
        model.addAttribute("boardbase", new BoardBase());
        return "secured/addBoardBase";
    }

    @PostMapping("/boardbaseAdded")
    public String boardbaseAdded(@ModelAttribute BoardBase boardbase) {
        Long returnValue = da.addBoardBase(Boardbase);
        System.out.println("return value is: " + returnValue);
        return "redirect:/";
    }

    @PostMapping("/reviewAdded")
    public String reviewAdded(@ModelAttribute Review review) {
        int returnValue;
        // if id exists, edit
        if (review.getId() != null) {
            returnValue = da.editReview(review);
        } else {
            // if id not exists, add
            returnValue = da.addReview(review);
        }
        System.out.println("return value is: " + returnValue);
        return "redirect:/" + review.getGameId() +
                "/reviews";
    }

    @GetMapping("/deleteReview/{id}")
    public String deleteReview(@PathVariable Long id) {
        Long gameId = da.getReview(id).getGameId();
        int returnValue = da.deleteReview(id);
        System.out.println("return value is: " + returnValue);
        return "redirect:/" + gameId + "/reviews";
    }

    @GetMapping("/user")
    public String goToUserSecured() {
        return "secured/user/index";
    }

    @GetMapping("/manager")
    public String goToManagerSecured() {
        return "secured/manager/index";
    }

    @GetMapping("/secured")
    public String goToSecured() {
        return "secured/gateway";
    }

    @GetMapping("/login")
    public String goToLogin() {
        return "login";
    }

    @GetMapping("/permission-denied")
    public String goToDenied() {
        return "error/permission-denied";
    }
}

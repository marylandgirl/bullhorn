package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("username",currentUserNameSimple());
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("logout")
    public String logout() {
        return "redirect:/login?logout=true";
    }

    @RequestMapping("/admin")
    public String admin(){
        return "admin";
    }

    @RequestMapping("/secure")
    public String secure(Principal principal, Model model) {
        String username = principal.getName();
        model.addAttribute("user",userRepository.findByUsername(username));
            return "secure";
    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistrationPage(@Valid @ModelAttribute("user") User user,
                                          BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "register";
        } else {
            model.addAttribute("message", "User Account Created");
            user.setEnabled(true);
            Role role = new Role(user.getUsername(), "ROLE_USER");
            Set<Role> roles = new HashSet<Role>();
            roles.add(role);
            roleRepository.save(role);
            userRepository.save(user);
        }
        return "index";
    }

    @RequestMapping("/username")
    public String currentUserNameSimple() {
        Authentication authentication = authenticationFacade.getAuthentication();
        return authentication.getName();
    }

    @RequestMapping("/messages")
    public String listMessages(Model model) {
        String username = currentUserNameSimple();
        User user = userRepository.findByUsername(username);
        Set<Message> messages = new HashSet<Message>();
        messages = user.getMessages();
        model.addAttribute("user", user);
        model.addAttribute("messages", messages);
        model.addAttribute("username",username);
        return "messages";
    }

    @RequestMapping("/detail/{id}")
    public String showMessage(@PathVariable("id") long id, Model model) {
        String msgText = messageRepository.findById(id).get().getMsgText();
        model.addAttribute("username",currentUserNameSimple());
        model.addAttribute("msg", msgText);
        model.addAttribute("id", id);
        return "onemessage";
    }
}

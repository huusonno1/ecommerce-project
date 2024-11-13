package ecom.demoecom.controller;

import ecom.demoecom.entity.Account;
import ecom.demoecom.repo.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    // Display the registration form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("account", new Account());
        return "account/register"; // Path to the registration template
    }

    // Handle registration form submission
    @PostMapping("/register")
    public String registerUser(@ModelAttribute Account account, Model model) {
        // Check if the username already exists in the database
        Account existingAccount = accountRepository.findByUsername(account.getUsername());
        if (existingAccount != null) {
            // Username already exists, show an error message
            model.addAttribute("error", "Username already taken. Please choose another one.");
            return "account/register"; // Return to the registration page with the error message
        }

        // Save the user details if the username is unique
        accountRepository.save(account);
        return "redirect:/login"; // Redirect to the login page
    }

    // Display the login form
    @GetMapping("/login")
    public String showLoginForm() {
        return "account/login"; // Path to the login template
    }

    // Handle login form submission
    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, Model model) {
        Account account = accountRepository.findByUsername(username);
        if (account != null && account.getPassword().equals(password)) {
            // Successful login
            model.addAttribute("username", account.getUsername());
            return "redirect:/home"; // Redirect to a welcome page or user dashboard
        } else {
            // Login failed
            model.addAttribute("error", "Invalid username or password");
            return "account/login"; // Path to the login template with error
        }
    }
}

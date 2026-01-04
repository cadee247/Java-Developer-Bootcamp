package eventai.eventai_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Marks this class as a REST controller
public class HomeController {

    @GetMapping("/") // Handles requests to the root URL
    public String home() {
        // Simple health / welcome endpoint
        return "Welcome to Event AI!";
    }
}

package eventai.eventai_backend.controller;

import eventai.eventai_backend.dto.EventDto;
import eventai.eventai_backend.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
@RestController // Marks class as REST controller
@RequestMapping("/api/ai") // Base URL for AI endpoints
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true") // Allow frontend CORS
public class AiController {

    private final EventService eventService; // Service to fetch events

    public AiController(EventService eventService) { // Constructor injection
        this.eventService = eventService;
    }

    @PostMapping("/respond") // Endpoint for chatbot messages
    public ResponseEntity<Map<String, Object>> respond(@RequestBody Map<String, String> payload) {

        String rawMessage = payload.getOrDefault("message", "").toLowerCase(); // Get raw message
        String userMessage = rawMessage.replaceAll("[^a-zA-Z ]", " ").replaceAll("\\s+", " ").trim(); // Clean message

        System.out.println("RAW MESSAGE: " + payload.get("message")); // Debug raw
        System.out.println("CLEAN MESSAGE: " + userMessage); // Debug cleaned

        Map<String, Object> response = new HashMap<>(); // Response map

        // Handle greetings
        if (userMessage.isEmpty() || userMessage.equals("hi") || userMessage.equals("hello") || userMessage.equals("hey")) {
            response.put("type", "greeting");
            response.put("reply", "Hello! What type of events are you looking for?\n\nCategories: Christmas, Tech, Music, Cooking");
            return ResponseEntity.ok(response); // Return greeting
        }

        String category = detectCategory(userMessage); // Detect category
        System.out.println("DETECTED CATEGORY: " + category); // Debug category

        if (category == null) { // Unknown category
            response.put("type", "error");
            response.put("reply", "Sorry, I didn’t understand that.\nTry one of these categories: Christmas, Tech, Music, Cooking");
            return ResponseEntity.ok(response); // Return error
        }

        List<EventDto> events = eventService.getAllEvents(); // Fetch all events
        System.out.println("TOTAL EVENTS IN DB: " + events.size()); // Debug total events

        List<String> keywords = categoryKeywords(category); // Get keywords
        List<EventDto> filtered = events.stream() // Filter events
                .filter(e -> matchesAnyKeyword(e, keywords))
                .sorted(Comparator.comparing(EventDto::getDate, Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toList());
        System.out.println("FILTERED EVENTS COUNT: " + filtered.size()); // Debug filtered count

        if (filtered.isEmpty()) { // No events
            response.put("type", "empty");
            response.put("reply", "I couldn’t find any " + category + " events in Johannesburg right now.");
            return ResponseEntity.ok(response); // Return empty
        }

        List<EventDto> hot = filtered.stream().limit(3).collect(Collectors.toList()); // Hot picks
        List<EventDto> more = filtered.stream().skip(3).collect(Collectors.toList()); // Remaining events

        List<Map<String, Object>> aiMessages = new ArrayList<>(); // Messages for frontend



        // Check if the 'hot' list is not empty
        if (!hot.isEmpty()) {
            // Add a header message to indicate Hot Picks for the given category
            aiMessages.add(Map.of("sender", "ai", "text", "🔥 Hot " + capitalize(category) + " Picks"));

            // Loop through each event in the 'hot' list
            for (int i = 0; i < hot.size(); i++) {
                // Retrieve the current event object
                EventDto event = hot.get(i);

                // Build and add a message for this event
                aiMessages.add(Map.of(
                        "sender", "ai",
                        "text",
                        // Format: "1. Event Title 📅 Date Description"
                        (i + 1) + ". " + event.getTitle() +
                                // Append date if available
                                (event.getDate() != null ? " 📅 " + event.getDate() : "") +
                                // Append description if available
                                (event.getDescription() != null ? " " + event.getDescription() : "")
                ));
            }
        }

        // Follow-up question
        aiMessages.add(Map.of("sender", "ai", "text",
                "What’s the vibe? 😎\n• Chill\n• Party\n• Outdoor\n• Foodie\n\nOr type another category 👀"));

        response.put("type", "events"); // Response type
        response.put("category", category); // Return category
        response.put("messages", aiMessages); // Return structured messages

        return ResponseEntity.ok(response); // Send response
    }

    private boolean matchesAnyKeyword(EventDto event, List<String> keywords) { // Check keywords
        String text = ((event.getTitle() == null ? "" : event.getTitle()) + " " +
                (event.getDescription() == null ? "" : event.getDescription())).toLowerCase();
        return keywords.stream().anyMatch(text::contains);
    }

    private List<String> categoryKeywords(String category) { // Map category -> keywords
        return switch (category) {
            case "tech" -> List.of("tech", "technology", "ai", "fintech", "startup", "cyber", "cloud", "digital");
            case "music" -> List.of("music", "dj", "concert", "festival", "live");
            case "cooking" -> List.of("cook", "cooking", "food", "chef", "kitchen");
            case "christmas" -> List.of("christmas", "xmas", "festive", "holiday");
            default -> List.of();
        };
    }

    private String detectCategory(String message) { // Detect category from message
        if (message.contains("christmas") || message.contains("xmas")) return "christmas";
        if (message.contains("tech") || message.contains("technology") || message.contains("ai") ||
                message.contains("startup") || message.contains("fintech")) return "tech";
        if (message.contains("music") || message.contains("dj") || message.contains("concert") || message.contains("festival")) return "music";
        if (message.contains("cook") || message.contains("food") || message.contains("cooking") || message.contains("chef")) return "cooking";
        return null;
    }

    private String capitalize(String str) { // Capitalize first letter
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}

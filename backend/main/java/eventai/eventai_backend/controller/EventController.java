package eventai.eventai_backend.controller;

import eventai.eventai_backend.dto.EventDto;
import eventai.eventai_backend.dto.CreateEventRequest;
import eventai.eventai_backend.dto.UpdateEventRequest;
import eventai.eventai_backend.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Marks this class as a REST controller
@RequestMapping("/api/events") // Base URL for event endpoints
public class EventController {

    private final EventService eventService;

    // Inject EventService
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping // Get all events
    public ResponseEntity<List<EventDto>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/{id}") // Get a single event by ID
    public ResponseEntity<EventDto> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @PostMapping // Create a new event
    public ResponseEntity<EventDto> createEvent(@RequestBody CreateEventRequest request) {
        return ResponseEntity.ok(eventService.createEvent(request));
    }

    @PutMapping("/{id}") // Update an existing event
    public ResponseEntity<EventDto> updateEvent(
            @PathVariable Long id,
            @RequestBody UpdateEventRequest request
    ) {
        return ResponseEntity.ok(eventService.updateEvent(id, request));
    }

    @DeleteMapping("/{id}") // Delete an event by ID
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build(); // Return 204 No Content
    }
}

package eventai.eventai_backend.dto;

import java.time.LocalDateTime;

public class EventDto {

    // Unique event ID
    private Long id;

    // Event title
    private String title;

    // Event description
    private String description;

    // Date and time of the event
    private LocalDateTime date;

    // ID of the user who created the event
    private Long createdByUserId;

    // ID of the event organizer
    private Long organizerId;

    // Default constructor
    public EventDto() {}

    // Constructor with all fields
    public EventDto(
            Long id,
            String title,
            String description,
            LocalDateTime date,
            Long createdByUserId,
            Long organizerId
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.createdByUserId = createdByUserId;
        this.organizerId = organizerId;
    }

    // Get event ID
    public Long getId() {
        return id;
    }

    // Set event ID
    public void setId(Long id) {
        this.id = id;
    }

    // Get event title
    public String getTitle() {
        return title;
    }

    // Set event title
    public void setTitle(String title) {
        this.title = title;
    }

    // Get event description
    public String getDescription() {
        return description;
    }

    // Set event description
    public void setDescription(String description) {
        this.description = description;
    }

    // Get event date and time
    public LocalDateTime getDate() {
        return date;
    }

    // Set event date and time
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    // Get ID of the user who created the event
    public Long getCreatedByUserId() {
        return createdByUserId;
    }

    // Set ID of the user who created the event
    public void setCreatedByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    // Get organizer ID
    public Long getOrganizerId() {
        return organizerId;
    }

    // Set organizer ID
    public void setOrganizerId(Long organizerId) {
        this.organizerId = organizerId;
    }
}

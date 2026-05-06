package com.example.backendhealth.dto;

import com.example.backendhealth.entities.Conversation.ConversationStatus;
import com.example.backendhealth.entities.Conversation.ConversationType;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversationDTO {
    private Long id;
    private String patientId;
    private String nutritionistId;
    private String coachId;
    private ConversationType type;
    private ConversationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<MessageDTO> messages;
}
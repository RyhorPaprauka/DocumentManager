package by.itacademy.documentmanager.dto;

import by.itacademy.documentmanager.model.DocumentType;
import lombok.Data;

@Data
public class DocumentResponseDto {

    private Long id;
    private String title;
    private DocumentType type;
}

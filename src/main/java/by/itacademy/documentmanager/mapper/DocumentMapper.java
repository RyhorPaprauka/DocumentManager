package by.itacademy.documentmanager.mapper;

import by.itacademy.documentmanager.dto.DocumentResponseDto;
import by.itacademy.documentmanager.model.Document;
import org.mapstruct.Mapper;

@Mapper
public interface DocumentMapper {

    DocumentResponseDto toDto(Document document);
}

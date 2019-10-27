package by.itacademy.documentmanager.service;

import by.itacademy.documentmanager.dto.DocumentFilterDto;
import by.itacademy.documentmanager.dto.DocumentResponseDto;
import by.itacademy.documentmanager.mapper.DocumentMapper;
import by.itacademy.documentmanager.model.Document;
import by.itacademy.documentmanager.model.DocumentType;
import by.itacademy.documentmanager.repository.DocumentRepository;
import by.itacademy.documentmanager.specification.DocumentSpecification;
import by.itacademy.documentmanager.specification.DocumentSpecificationBuilder;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.Metadata;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final DbxClientV2 dropBoxClient;
    private final DocumentMapper documentMapper;

    public void upload(MultipartFile file, String title, DocumentType type) throws Exception {
        try (InputStream inputStream = file.getInputStream()) {
            Metadata metadata = dropBoxClient.files()
                    .uploadBuilder("/documents/" + file.getOriginalFilename())
                    .uploadAndFinish(file.getInputStream());

            documentRepository.save(Document.builder()
                    .title(title)
                    .type(type)
                    .uploadDate(LocalDateTime.now())
                    .url(metadata.getPathLower())
                    .build());
        }
    }

    public void download(HttpServletResponse response, Long id) throws Exception {
        Document document = documentRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + document.getTitle());

        try (ServletOutputStream outputStream = response.getOutputStream()) {
            dropBoxClient.files().downloadBuilder(document.getUrl()).download(outputStream);
        }
    }

    public List<DocumentResponseDto> search(DocumentFilterDto filter) {
        return documentRepository.findAll(getSpecification(filter)).stream()
                .map(documentMapper::toDto)
                .collect(Collectors.toList());
    }


    @SuppressWarnings("unchecked")
    private Specification<Document> getSpecification(DocumentFilterDto filter) {
        DocumentSpecificationBuilder dsb = new DocumentSpecificationBuilder();

        if (filter.getTitle() != null) {
            dsb.add(DocumentSpecification.titleContains(filter.getTitle()));
        }
        if (filter.getType() != null) {
            dsb.add(DocumentSpecification.hasType(filter.getType()));
        }
        return dsb.getSpecification();
    }
}

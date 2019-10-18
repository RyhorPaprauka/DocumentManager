package by.itacademy.documentmanager.controller;

import by.itacademy.documentmanager.dto.DocumentFilterDto;
import by.itacademy.documentmanager.dto.DocumentResponseDto;
import by.itacademy.documentmanager.model.DocumentType;
import by.itacademy.documentmanager.service.DocumentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/v1/document")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity upload(@RequestParam("file") MultipartFile file,
                                 @RequestParam("title") String title,
                                 @RequestParam("type") DocumentType type) throws Exception {
        documentService.upload(file, title, type);
        return ResponseEntity.ok(title + " successfully uploaded");
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void download(HttpServletResponse response, @PathVariable Long id) throws Exception {
        documentService.download(response, id);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<List<DocumentResponseDto>> search(
            @RequestBody DocumentFilterDto documentFilterDto) {
        return ResponseEntity.ok(documentService.search(documentFilterDto));
    }
}

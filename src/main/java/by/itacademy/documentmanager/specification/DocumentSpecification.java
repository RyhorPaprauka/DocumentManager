package by.itacademy.documentmanager.specification;

import by.itacademy.documentmanager.model.Document;
import by.itacademy.documentmanager.model.DocumentType;
import org.springframework.data.jpa.domain.Specification;

public class DocumentSpecification {

    public static Specification<Document> titleContains(String title) {
        return ((doc, cq, cb) -> cb.like(doc.get("title"), "%" + title + "%"));
    }

    public static Specification<Document> hasType(DocumentType type) {
        return (doc, cq, cb) -> cb.equal(doc.get("type"), type);
    }
}

package by.itacademy.documentmanager.specification;

import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

@Getter
public class DocumentSpecificationBuilder<Document> {

    private Specification<Document> specification;

    public void add(Specification<Document> documentSpecification) {
        specification = Objects.isNull(specification)
                ? Specification.where(documentSpecification)
                : specification.and(documentSpecification);
    }
}

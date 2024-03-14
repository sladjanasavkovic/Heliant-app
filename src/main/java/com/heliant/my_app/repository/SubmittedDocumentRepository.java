package com.heliant.my_app.repository;

import com.heliant.my_app.model.Document;
import com.heliant.my_app.model.SubmittedDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubmittedDocumentRepository extends JpaRepository<SubmittedDocument, Integer> {

    SubmittedDocument findFirstByDocument(Document document);

    @Query(nativeQuery = true,
            value = "select count(*) from submitted_documents sd where sd.date_created BETWEEN :startDateTime AND :endDateTime")
    Integer countSubmittedDocumentForLastDay(@Param("startDateTime") LocalDateTime startDateTime,
                                             @Param("endDateTime") LocalDateTime endDateTime);

    @Override
    @Query(value = """
            select sd from SubmittedDocument sd
            left join fetch sd.submittedDocumentFields sdf
            left join fetch sdf.documentField
            """)
    List<SubmittedDocument> findAll();

    @Override
    @Query(value = """
                                select sd from SubmittedDocument sd
                                left join fetch sd.submittedDocumentFields sdf
                                left join fetch sdf.documentField
                                where sd.id = :id
            """)
    Optional<SubmittedDocument> findById(@Param("id") Integer id);

}

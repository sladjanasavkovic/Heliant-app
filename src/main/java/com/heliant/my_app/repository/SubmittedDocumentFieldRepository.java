package com.heliant.my_app.repository;

import com.heliant.my_app.model.DocumentField;
import com.heliant.my_app.model.SubmittedDocument;
import com.heliant.my_app.model.SubmittedDocumentField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface SubmittedDocumentFieldRepository extends JpaRepository<SubmittedDocumentField, Integer> {

    SubmittedDocumentField findFirstBySubmittedDocument(SubmittedDocument submittedDocument);

    SubmittedDocumentField findFirstByDocumentField(DocumentField documentField);
    Set<SubmittedDocumentField> findAllBySubmittedDocumentId(Integer id);

    @Override
    @Query("""
           select sdf from SubmittedDocumentField sdf
           left join fetch sdf.documentField
           """)
    List<SubmittedDocumentField> findAll();

    @Override
    @Query("""
           select sdf from SubmittedDocumentField sdf
           left join fetch sdf.documentField
           where sdf.id = :id
           """)
    Optional<SubmittedDocumentField> findById(@Param("id") Integer id);

}

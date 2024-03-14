package com.heliant.my_app.repository;

import com.heliant.my_app.model.Document;
import com.heliant.my_app.model.DocumentField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentFieldRepository extends JpaRepository<DocumentField, Integer> {

    DocumentField findFirstByDocument(Document document);

}

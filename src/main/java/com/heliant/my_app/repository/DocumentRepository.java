package com.heliant.my_app.repository;

import com.heliant.my_app.model.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {

    @Override
    Page<Document> findAll(Pageable pageable);
}

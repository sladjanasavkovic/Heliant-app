package com.heliant.my_app.model;

import com.heliant.my_app.model.enums.FieldType;
import com.heliant.my_app.model.listeners.Auditable;
import com.heliant.my_app.model.listeners.EntityListener;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;


@Entity
@EntityListeners(EntityListener.class)
@Table(name = "DocumentFields")
@Getter
@Setter
public class DocumentField implements Auditable {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer orderNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FieldType type;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dateCreated;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime lastUpdated;

    @OneToMany(mappedBy = "documentField")
    private Set<SubmittedDocumentField> submittedDocumentFields;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "modified_by_id")
    private User modifiedBy;

    @Override
    public void setCreatedBy(User user) {
        this.createdBy = user;
    }

    @Override
    public void setModifiedBy(User user) {
        this.modifiedBy = user;
    }

}

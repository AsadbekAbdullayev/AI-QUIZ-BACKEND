package uz.genesis.aiquest.webserver.models.entity.base;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
public class BaseEntitySequentialID implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @CreationTimestamp
    @Column(updatable = false)
    protected Timestamp createdAt;

    @UpdateTimestamp
    protected Timestamp modifiedAt;

    @CreatedBy
    protected UUID createdBy;


    public static Sort getSortByCreatedAtDesc() {
        return Sort.by(_createdAt).descending();
    }

    public static final String _createdAt = "createdAt";
}

package uz.genesis.aiquest.webserver.models.entity.base;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
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
public class BaseEntityUID implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(unique = true)
    protected UUID id;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    protected Timestamp createdAt;

    @UpdateTimestamp
    protected Timestamp modifiedAt;

    @CreatedBy
    @Column(name = "created_by")
    protected UUID createdBy;

    public static final String createdAtField = "createdAt";
}

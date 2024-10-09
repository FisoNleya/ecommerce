package com.manica.productscatalogue.auction;

import com.manica.productscatalogue.subscriptions.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedBy;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BlackList  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blacklist_id", nullable = false)
    private Long blacklistId;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "user_id",foreignKey = @ForeignKey(name = "user_id_blacklist_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @CreatedBy
    private String createdBy;

    @CreationTimestamp
    private LocalDateTime createdAt;
}

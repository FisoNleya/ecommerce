package com.manica.productscatalogue.subscriptions.user;

import com.manica.productscatalogue.auth.dtos.Permission;
import com.manica.productscatalogue.subscriptions.vendor.Vendor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;


@Data
@Entity
@Builder
@Table(name = "user_right")
@NoArgsConstructor
@AllArgsConstructor
public class UserRight {

    @Id
    private Long userRightId;

    @Enumerated(EnumType.STRING)
    private Permission permission;


    @ManyToOne
    @JoinColumn(name = "vendor_id",referencedColumnName = "vendor_id",
            foreignKey = @ForeignKey(name = "vendor_id_user_right_fk"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Vendor vendor;

    private LocalDateTime createdAt;
    private String grantedBy;

}

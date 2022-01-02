package com.amk.lms.models.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@Table(name = "et_token", schema = "amkdb")
public class TokenDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "token")
    private String token;

    @Column(name = "refresh_token", unique = true)
    private String refreshToken;

    @Column(name = "is_revoke")
    private String isRevoke = "N";

    @Column(name = "is_refresh")
    private String isRefresh = "N";

    @Column(name = "ref_id")
    private Integer refId;

    @OneToOne
    @JoinColumn(name = "ref_id", referencedColumnName = "id", insertable = false, updatable = false)
    private TokenDto tokenDto;

    @Column(name = "user_ref")
    private int userRef;

    @OneToOne
    @JoinColumn(name = "user_ref", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User user;


    @Column(name = "expired")
    private Instant expired;

}

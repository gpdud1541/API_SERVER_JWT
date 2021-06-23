package com.api.dex.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@DynamicInsert
@Data
@Entity
@Table(name = "member")
public class Member extends BaseEntity implements Serializable  {

    private static final long serialVersionUID = -6430942442101448953L;

    @Column(unique = true, length = 50)
    private String account;

    @Column(length = 100)
    private String password;

    @Column(length = 100)
    private String name;

    @Column(length = 1000)
    private String token;

    @Builder
    public Member(String account, String password, String name, String token) {
        this.account = account;
        this.password = password;
        this.name = name;
        this.token = token;

    }

}

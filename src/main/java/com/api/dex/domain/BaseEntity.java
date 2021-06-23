package com.api.dex.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = -8596858085311956445L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "INT(11)")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createTimestamp;

    @Temporal(TemporalType.TIMESTAMP)
    //@Column(nullable = true, columnDefinition = "TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP")
    // mysql 5.7에서는 TIMESTAMP DEFAULT NULL이 적용되지 않음.
    @Column(nullable = true, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date updateTimestamp;

//    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
//    private boolean del;

    @PrePersist // entity 값이 persist 영역으로 넘어가기 전(DB 저장 전)에 행해져야 할 기능을 정의
    protected void onCreate() {
        createTimestamp = Timestamp.valueOf(LocalDateTime.now());
    }

    @PreUpdate // entity 값이 update 전에 행해져야 할 기능을 정의
    protected void onUpdate() {
        updateTimestamp = Timestamp.valueOf(LocalDateTime.now());
    }

}


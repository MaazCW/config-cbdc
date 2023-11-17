package com.opl.cbdc.config.domain;


import lombok.*;

import javax.persistence.*;
import java.io.*;
import java.util.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "nrlm_api_audit", catalog = "ans_config", schema = "ans_config")
public class NRLMAPIAudit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "url")
    private String url;

    public interface TypeMaster {
        public static final String LOGIN_API = "LOGIN_API";
        public static final String APPLICATION_LIST = "APPLICATION_LIST";
        public static final String APPLICATION_DETAILS = "APPLICATION_DETAILS";
    }

    @Column(name = "type")
    private String type;

    @Column(name = "is_success")
    private Boolean isSuccess;

    @Column(name = "message")
    private String message;

    @OneToOne
    @JoinColumn(name = "log_id")
    private ReqResLogs reqResLogs;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdDate;

    @Builder.Default
    @Column(name = "is_active")
    private Boolean isActive = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date modifiedDate;



}

package com.opl.cbdc.config.domain;

import lombok.*;

import javax.persistence.*;
import java.io.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "api_restrict_config", catalog = "ans_config", schema = "ans_config")
public class APIRestrictConfig implements Serializable {

    private static final long serialVersionUID = 5596459373984970632L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "minutes")
    private Integer minutes;

    @Column(name = "max_attempt")
    private Integer maxAttempt;

    @Column(name = "api_code")
    private String apiCode;

    @Column(name = "messages")
    private String messages;

    @Column(name = "is_active")
    private Boolean isActive;
}

package com.opl.cbdc.config.domain;

import lombok.*;

import javax.persistence.*;
import java.io.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "multiple_bank_config", catalog = "ans_config", schema = "ans_config")
public class MultipleBankConfig  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "bank_id")
    private Long bankId;

    @Column(name = "scheme_id")
    private Integer schemeId;

    @Column(name = "offline_days")
    private Integer offlineDays;

    @Column(name = "online_days")
    private Integer onlineDays;

    @Column(name = "is_active")
    private Boolean isActive;

}


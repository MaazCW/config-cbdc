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
@Table(name = "api_restrict_audit", catalog = "ans_config", schema = "ans_config")
public class APIRestrictAudit implements Serializable {

	private static final long serialVersionUID = 4465465461L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restrict_id")
    private APIRestrictConfig restrictId;

    @Column(name = "value")
    private Long value;

    @Column(name = "messages")
    private String messages;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "is_active")
    private Boolean isActive;
    
    @Column(name="email")
    private String email;

}

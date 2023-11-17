package com.opl.cbdc.config.domain;

import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.io.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "req_res_logs", catalog = "ans_config", schema = "ans_config")
public class ReqResLogs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "request")
    @Type(type="text")
    @ColumnTransformer(read = "UNCOMPRESS(request)", write = "COMPRESS(?)")
    private String request;

    @Column(name = "response")
    @ColumnTransformer(read = "UNCOMPRESS(request)", write = "COMPRESS(?)")
    @Type(type="text")
    private String response;


}

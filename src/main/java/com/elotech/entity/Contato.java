package com.elotech.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "CONTATO")
public class Contato extends BaseEntity{

    @Column(length = 200, nullable = false)
    private String name;

    @Column(precision = 12, nullable = false)
    private Long phone;

    @Column(precision = 12, nullable = false)
    private String email;
}

package com.elotech.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "CONTATO")
public class Contato extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pessoa", nullable = false)
    private Pessoa pessoa;

    @Column(length = 200, nullable = false)
    private String nome;

    @Column(precision = 12, nullable = false)
    private Long telefone;

    @Column(precision = 12, nullable = false)
    private String email;
}

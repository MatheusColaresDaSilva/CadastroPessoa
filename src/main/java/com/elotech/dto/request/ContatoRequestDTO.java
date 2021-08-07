package com.elotech.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContatoRequestDTO {
    private String nome;
    private Long telefone;
    private String email;
}

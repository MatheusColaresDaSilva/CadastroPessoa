package com.elotech.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContatoResponseDTO {
    private Long id;
    private Long pessoa;
    private String nome;
    private Long telefone;
    private String email;
}

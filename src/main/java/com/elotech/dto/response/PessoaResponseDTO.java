package com.elotech.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PessoaResponseDTO {
    private Long id;
    private String name;
    private String sin;
    private LocalDate birthDate;
    private List<ContatoResponseDTO> contatos;
}

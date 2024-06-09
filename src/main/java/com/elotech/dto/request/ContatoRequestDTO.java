package com.elotech.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContatoRequestDTO {
    private String name;
    private Long phone;
    private String email;
}

package com.elotech.controller;

import com.elotech.dto.response.PessoaResponseDTO;
import com.elotech.dto.response.ResponseDTO;
import com.elotech.service.PessoaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pessoa")
public class PessoaController extends BaseController{

    private PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<PessoaResponseDTO>> consultaPorId(@PathVariable Long id) {
        PessoaResponseDTO response = pessoaService.consultaPorId(id);
        return ResponseEntity.ok(new ResponseDTO<>(response));
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<PessoaResponseDTO>>> consultaTodos() {
        List<PessoaResponseDTO> response = pessoaService.consultaTodos();
        return ResponseEntity.ok(new ResponseDTO<>(response));
    }
}

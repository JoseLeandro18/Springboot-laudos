package com.estudos.springboot_laudos_medicos.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "laudos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Laudo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cpfPaciente;

    private String nomePaciente;

    private String emailPaciente;

    private String exameTipo;

    private Long codigoPaciente;

    private String nomeArquivoPdf;

    private LocalDate dataEnvio;

}

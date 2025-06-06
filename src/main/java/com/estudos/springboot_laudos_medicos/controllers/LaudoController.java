package com.estudos.springboot_laudos_medicos.controllers;

import com.estudos.springboot_laudos_medicos.entities.Laudo;
import com.estudos.springboot_laudos_medicos.reposotories.LaudoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

@Controller
public class LaudoController {

    private final String pastaUpload = System.getProperty("user.dir") + File.separator + "laudos";
    @Autowired
    private LaudoRepository laudoRepository;

    @GetMapping("/adm")
    public String formularioEnvio() {
        return "envioLaudo"; // Arquivo HTML
    }

    @PostMapping("/adm/envio-laudo")
    public String enviarLaudo(
            @RequestParam("cpfPaciente") Long cpfPaciente,
            @RequestParam("nomePaciente") String nomePaciente,
            @RequestParam("emailPaciente") String emailPaciente,
            @RequestParam("exameTipo") String exameTipo,
            @RequestParam("codigoPaciente") Long codigoPaciente,
            @RequestParam("nomeArquivoPdf") MultipartFile nomeArquivoPdf,
            Model model
    ) {
        try {
            if (nomeArquivoPdf.isEmpty()) {
                model.addAttribute("mensagem", "Nenhum arquivo enviado.");
                return "envioLaudo";
            }

            // Esse metodo que cria uma pasta se não existir
            File pasta = new File(pastaUpload);
            if (!pasta.exists()) pasta.mkdir();

            String nomeArquivo = System.currentTimeMillis() + "_" + nomeArquivoPdf.getOriginalFilename();
            File destino = new File(pastaUpload + File.separator + nomeArquivo);
            nomeArquivoPdf.transferTo(destino);

            //Cria e salva o laudo no banco de dados
            Laudo laudosMedicos = new Laudo();
            laudosMedicos.setCpfPaciente(cpfPaciente);
            laudosMedicos.setNomePaciente(nomePaciente);
            laudosMedicos.setEmailPaciente(emailPaciente);
            laudosMedicos.setExameTipo(exameTipo);
            laudosMedicos.setCodigoPaciente(codigoPaciente);
            laudosMedicos.setNomeArquivoPdf(nomeArquivo);
            laudosMedicos.setDataEnvio(LocalDate.now());

            laudoRepository.save(laudosMedicos);
            model.addAttribute("mensagem", "Laudo enviado com sucesso!");

        } catch (IOException e) {
            model.addAttribute("mensagem", "Erro ao salvar o arquivo, tente novamente!");
        }

        return "envioLaudo";
    }

}

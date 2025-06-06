package com.estudos.springboot_laudos_medicos.reposotories;

import com.estudos.springboot_laudos_medicos.entities.Laudo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LaudoRepository extends JpaRepository<Laudo, Long> {
    // adicionar buscas personalizadas depois (ex: por CPF)
}

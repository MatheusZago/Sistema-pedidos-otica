package com.matheusluizago.backend.validator;

import com.matheusluizago.backend.exceptions.DuplicateRegisterException;
import com.matheusluizago.backend.model.Laboratorio;
import com.matheusluizago.backend.repository.LaboratorioRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LaboratorioValidator {

    private LaboratorioRepository repository;

    public LaboratorioValidator(LaboratorioRepository repository){
        this.repository = repository;
    }

    public void validate(Laboratorio laboratorio){
        if (existsRegisteredLaboratorio(laboratorio)) {

            throw new DuplicateRegisterException("Laboratorio já registrado!");
        }
    }

    private boolean existsRegisteredLaboratorio(Laboratorio laboratorio) {
        Optional<Laboratorio> laboratorioEncontrado = repository.findByCnpj(laboratorio.getCnpj());

        //Cadastro novo
        if(laboratorio.getId() == null){
            return laboratorioEncontrado.isPresent();
        }

        //Update, ignora o próprio registro
        return laboratorioEncontrado.isPresent() && !laboratorio.getId().equals(laboratorioEncontrado.get().getId());
    }
}

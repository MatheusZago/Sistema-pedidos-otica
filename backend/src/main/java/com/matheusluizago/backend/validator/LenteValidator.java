package com.matheusluizago.backend.validator;

import com.matheusluizago.backend.exceptions.DuplicateRegisterException;
import com.matheusluizago.backend.model.Lente;
import com.matheusluizago.backend.repository.LenteRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LenteValidator {


    private LenteRepository repository;

    public LenteValidator(LenteRepository repository){
        this.repository = repository;
    }

    public void validate(Lente lente){
        if(existsRegisteredLente(lente)){
            throw new DuplicateRegisterException("Lente já cadastrada!");
        }
    }

    private boolean existsRegisteredLente(Lente lente) {
        Optional<Lente> lenteEncontrada = repository.findByTipoLenteAndCustoAndTratamentoAndIndiceAndValorVenda(
                lente.getTipoLente(), lente.getCusto(), lente.getTratamento(), lente.getIndice(), lente.getValorVenda()
        );

        //Novo registro
        if(lente.getId() == null){
            return lenteEncontrada.isPresent();
        }

        //Update
        return lenteEncontrada.isPresent() && !lente.getId().equals(lenteEncontrada.get().getId());

    }


}

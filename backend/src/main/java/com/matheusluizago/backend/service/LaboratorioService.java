package com.matheusluizago.backend.service;

import com.matheusluizago.backend.dto.LaboratorioRegisterDto;
import com.matheusluizago.backend.dto.LaboratorioResponseDto;
import com.matheusluizago.backend.mapper.LaboratorioMapper;
import com.matheusluizago.backend.model.Laboratorio;
import com.matheusluizago.backend.repository.LaboratorioRepository;
import com.matheusluizago.backend.repository.specs.LaboratorioSpecs;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LaboratorioService {

    private final LaboratorioRepository repository;
    private final LaboratorioMapper mapper;

    public LaboratorioService(LaboratorioRepository repository, LaboratorioMapper mapper) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public Laboratorio save(LaboratorioRegisterDto labDto) {

        Laboratorio lab = mapper.toEntity(labDto);

        return repository.save(lab);
    }

//    public List<LaboratorioResponseDto> searchByExample(Integer id, String nome, String endereco) {
//        var lab = new Laboratorio();
//        lab.setId(id);
//        lab.setNome(nome);
//        lab.setEndereco(endereco);
//
//        ExampleMatcher matcher = ExampleMatcher
//                .matching()
//                .withIgnoreNullValues()
//                .withIgnoreCase()
//                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
//
//        Example<Laboratorio> labExample = Example.of(lab, matcher);
//
//        return repository.findAll(labExample)
//                .stream()
//                .map(mapper::toDto)
//                .toList();
//    }

    public List<LaboratorioResponseDto> search(
            Integer id,
            String nome,
            String endereco
    ){
        Specification<Laboratorio> spec = Specification
                .where(LaboratorioSpecs.idEqual(id))
                .and(LaboratorioSpecs.nomeLike(nome))
                .and(LaboratorioSpecs.enderecoLike(endereco));

        return repository.findAll(spec)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Transactional
    public LaboratorioResponseDto update(Integer id, LaboratorioRegisterDto dto){

        Laboratorio laboratorio = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clientenão encontrado"));

        mapper.updateLab(laboratorio, dto);

        Laboratorio atualizado = repository.save(laboratorio);

        return mapper.toDto(atualizado);

    }
}

package com.matheusluizago.backend.service;

import com.matheusluizago.backend.mapper.ClienteMapper;
import com.matheusluizago.backend.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith((MockitoExtension.class))
public class ClienteServiceTest {

    @Mock
    private ClienteRepository repository;

    @Mock
    private ClienteMapper mapper;

    @InjectMocks
    private ClienteService service;

    @Test
    void saveCliente_WithValidData_ReturnCliente(){

    }
}

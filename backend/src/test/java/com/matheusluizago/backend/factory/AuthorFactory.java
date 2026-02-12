package com.matheusluizago.backend.factory;

import com.matheusluizago.backend.dto.clienteDto.ClienteRegisterDto;
import com.matheusluizago.backend.dto.clienteDto.ClienteResponseDto;
import com.matheusluizago.backend.dto.clienteDto.ClienteUpdateDto;
import com.matheusluizago.backend.model.Cliente;

public class AuthorFactory {

    private static final Integer DEFAULT_ID = 1;
    private static final String DEFAULT_NOME = "Matheus";
    private static final String DEFAULT_EMAIL = "matheus@email.com";
    private static final String DEFAULT_TELEFONE = "11912345678";
    private static final String DEFAULT_FOTO = "url da foto";

    public static Cliente createValidCliente(){
        Cliente cliente = new Cliente();
        cliente.setId(DEFAULT_ID);
        cliente.setNome(DEFAULT_NOME);
        cliente.setEmail(DEFAULT_EMAIL);
        cliente.setTelefone(DEFAULT_TELEFONE);
        cliente.setFoto(DEFAULT_FOTO);
        return cliente;
    }

    public static Cliente createValidClienteWithoutId(){
        Cliente cliente = createValidCliente();
        cliente.setId(null);
        return cliente;
    }

    public static Cliente createInvalidNomeCliente(){
        Cliente cliente = createInvalidNomeCliente();
        cliente.setNome("");
        return cliente;
    }

    public static Cliente createInvalidEmailCliente() {
        Cliente cliente = createValidCliente();
        cliente.setEmail("email-invalido");
        return cliente;
    }

    public static Cliente createInvalidTelefoneCliente() {
        Cliente cliente = createValidCliente();
        cliente.setTelefone("telefone-invalido");
        return cliente;
    }

    //REGISTER DTO
    public static ClienteRegisterDto createValidRegisterClienteDto() {
        return new ClienteRegisterDto(
                DEFAULT_NOME,
                DEFAULT_EMAIL,
                DEFAULT_TELEFONE,
                DEFAULT_FOTO
        );
    }

    public static ClienteRegisterDto createInvalidNomeClienteRegister(){
        return new ClienteRegisterDto(
                "",
                DEFAULT_EMAIL,
                DEFAULT_TELEFONE,
                DEFAULT_FOTO
        );
    }

    public static ClienteRegisterDto createInvalidEmailClienteRegisterDto() {
        return new ClienteRegisterDto(
                DEFAULT_NOME,
                "email-invalido",
                DEFAULT_TELEFONE,
                DEFAULT_FOTO
        );
    }

    public static ClienteRegisterDto createInvalidTelefoneClienteRegisterDto() {
        return new ClienteRegisterDto(
                DEFAULT_NOME,
                DEFAULT_EMAIL,
                "telefone-invalido",
                DEFAULT_FOTO
        );
    }


    //UPDATE DTO
    public static ClienteUpdateDto createValidClienteUpdateDto() {
        return new ClienteUpdateDto(
                DEFAULT_NOME,
                DEFAULT_EMAIL,
                DEFAULT_TELEFONE,
                DEFAULT_FOTO
        );
    }

    public static ClienteUpdateDto createInvalidEmailClienteUpdateDto() {
        return new ClienteUpdateDto(
                DEFAULT_NOME,
                "email-invalido",
                DEFAULT_TELEFONE,
                DEFAULT_FOTO
        );
    }

    public static ClienteUpdateDto createInvalidTelefoneClienteUpdateDto() {
        return new ClienteUpdateDto(
                DEFAULT_NOME,
                DEFAULT_EMAIL,
                "telefone-invalido",
                DEFAULT_FOTO
        );
    }

    //RESPONSE DTO
    public static ClienteResponseDto createValidClienteResponseDto() {
        return new ClienteResponseDto(
                DEFAULT_ID,
                DEFAULT_NOME,
                DEFAULT_EMAIL,
                DEFAULT_TELEFONE,
                DEFAULT_FOTO
        );
    }





}

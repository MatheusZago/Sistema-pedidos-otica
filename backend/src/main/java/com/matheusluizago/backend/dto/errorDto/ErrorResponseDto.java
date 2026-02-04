package com.matheusluizago.backend.dto.errorDto;

public record ErrorResponseDto(
        Integer status,
        String error,
        String message
) {
}

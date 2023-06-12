package lv.martins.homework.service.dto;

import jakarta.validation.constraints.NotNull;

public record KidDTO(
        long id,
        @NotNull String name,
        @NotNull int age,
        @NotNull String ticketNumber
) {
}

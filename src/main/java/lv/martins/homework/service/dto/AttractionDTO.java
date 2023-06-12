package lv.martins.homework.service.dto;

public record AttractionDTO(
    long id,
    AttractionType attractionType,
    int size
) {
}

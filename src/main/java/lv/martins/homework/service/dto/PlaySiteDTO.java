package lv.martins.homework.service.dto;

import java.util.List;

public record PlaySiteDTO (
        long id,
        String name,
        List<AttractionDTO> attractions,
        Integer utilization
) {
}

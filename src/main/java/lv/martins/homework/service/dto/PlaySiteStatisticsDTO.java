package lv.martins.homework.service.dto;

public record PlaySiteStatisticsDTO(
        Long totalPlaySitesSize,
        Long totalKidsOnPlaySites,
        Long totalKidsInQueue,
        Integer totalUtilization
) {
}

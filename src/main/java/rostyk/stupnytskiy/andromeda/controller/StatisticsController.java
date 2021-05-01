package rostyk.stupnytskiy.andromeda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rostyk.stupnytskiy.andromeda.dto.response.statistics.advertisement.GoodsAdvertisementStatisticsResponse;
import rostyk.stupnytskiy.andromeda.dto.response.statistics.diagrams.ColumnDiagramDataResponse;
import rostyk.stupnytskiy.andromeda.repository.feedback.GoodsAdvertisementFeedbackRepository;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.GoodsAdvertisementService;
import rostyk.stupnytskiy.andromeda.service.statistics.StatisticsService;
import rostyk.stupnytskiy.andromeda.service.statistics.advertisement.UserAdvertisementViewService;

@CrossOrigin
@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    // delete
    @Autowired
    private UserAdvertisementViewService userAdvertisementViewService;

    // delete
    @Autowired
    private GoodsAdvertisementService goodsAdvertisementService;

    // delete
    @Autowired
    private GoodsAdvertisementFeedbackRepository goodsAdvertisementFeedbackRepository;

    @GetMapping("year-orders")
    public ColumnDiagramDataResponse getOrdersDataByYear(int year, Long id) {
        return statisticsService.getAdvertisementOrdersYearStatistics(id, year);
    }

    @GetMapping("year-views")
    public ColumnDiagramDataResponse getViewsDataByYear(int year, Long id) {
        return statisticsService.getAdvertisementViewsYearStatistics(id, year);
    }

    @GetMapping("advertisement")
    public GoodsAdvertisementStatisticsResponse getAdvertisementStaticsResponse(Long id) {
        return statisticsService.getAdvertisementStaticsResponse(id);
    }

    @GetMapping("advertisement-date")
    public String getAdvertisementCreationDate(Long id) {
        return statisticsService.getAdvertisementCreationDate(id);
    }

    @GetMapping("test")
    public double getTest() {
        return goodsAdvertisementFeedbackRepository.getAverageRatingForLast30DaysByGoodsAdvertisement( 1L);
    }

    @GetMapping("feedbacks-last-month")
    public ColumnDiagramDataResponse getFeedbacksStatisticsByLastMonth(Long id) {
        return statisticsService.getAdvertisementFeedbacksLastMonthStatistics(id);
    }

    @GetMapping("feedbacks-month")
    public ColumnDiagramDataResponse getFeedbacksStatisticsByLastMonth(Long id, int month, int year) {
        return statisticsService.getAdvertisementFeedbacksByMonthAndYear(id, month, year);
    }
    @GetMapping("feedbacks-year")
    public ColumnDiagramDataResponse getFeedbacksStatisticsByYear(Long id, int year) {
        return statisticsService.getAdvertisementFeedbacksByYear(id, year);
    }
    @GetMapping("feedbacks-all")
    public ColumnDiagramDataResponse getFeedbacksStatistics(Long id) {
        return statisticsService.getAdvertisementFeedbacks(id);
    }
}

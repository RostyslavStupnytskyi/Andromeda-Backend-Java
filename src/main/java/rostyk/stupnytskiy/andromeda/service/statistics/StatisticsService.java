package rostyk.stupnytskiy.andromeda.service.statistics;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.response.statistics.advertisement.GoodsAdvertisementStatisticsResponse;
import rostyk.stupnytskiy.andromeda.dto.response.statistics.diagrams.ColumnDiagramDataResponse;
import rostyk.stupnytskiy.andromeda.dto.response.statistics.diagrams.DiagramColumnResponse;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.repository.feedback.GoodsAdvertisementFeedbackRepository;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.GoodsAdvertisementService;
import rostyk.stupnytskiy.andromeda.service.statistics.advertisement.UserAdvertisementViewService;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticsService {

    @Autowired
    private GoodsAdvertisementService goodsAdvertisementService;

    @Autowired
    private UserAdvertisementViewService userAdvertisementViewService;

    @Autowired
    private GoodsAdvertisementFeedbackRepository goodsAdvertisementFeedbackRepository;

    public ColumnDiagramDataResponse getAdvertisementOrdersYearStatistics(Long advertisementId, int year) {
        GoodsAdvertisement advertisement = goodsAdvertisementService.findById(advertisementId);
        List<DiagramColumnResponse> columns = new ArrayList<>();
        int max = 0;
        for (int i = 1; i <= 12; i++) {
            DiagramColumnResponse column = new DiagramColumnResponse();
            if (canShowStatistics(year, i, advertisement)) {
                column.setValue(advertisement.getCountOrdersByYearAndMonth(year, Month.of(i)));
                column.setShowable(true);
                if (max < column.getValue()) max = column.getValue();
            }
            columns.add(column);
        }
        max = getMaxDiagramValueByMaxColumnValue(max);
        for (DiagramColumnResponse column : columns)
            column.setPercent(getPercent(column.getValue(), max));
        return new ColumnDiagramDataResponse(max, 0, null, columns);
    }

    public ColumnDiagramDataResponse getAdvertisementViewsYearStatistics(Long advertisementId, int year) {
        GoodsAdvertisement advertisement = goodsAdvertisementService.findById(advertisementId);
        List<DiagramColumnResponse> columns = new ArrayList<>();

        int max = 0;

        for (int i = 1; i <= 12; i++) {
            DiagramColumnResponse column = new DiagramColumnResponse();
            if (canShowStatistics(year, i, advertisement)) {
                column.setValue(userAdvertisementViewService.getViewCountByAdvertisementAndYearAndMonth(advertisement, year, i));
                column.setShowable(true);
                if (max < column.getValue()) max = column.getValue();
            }
            columns.add(column);
        }

        max = getMaxDiagramValueByMaxColumnValue(max);

        for (DiagramColumnResponse column : columns)
            column.setPercent(getPercent(column.getValue(), max));

        return new ColumnDiagramDataResponse(max, 0, null, columns);
    }

    public ColumnDiagramDataResponse getAdvertisementFeedbacksLastMonthStatistics(Long advertisementId) {
        List<DiagramColumnResponse> columns = new ArrayList<>();
        int sum = 0;
        double elements = 0;
        for (int i = 5; i >= 1; i--) {
            DiagramColumnResponse column = new DiagramColumnResponse();
            column.setShowable(true);
            column.setValue(goodsAdvertisementFeedbackRepository.getCountOfFeedbacksForLast30DaysByGoodsAdvertisementAndRating(i, advertisementId));
            columns.add(column);
            sum = sum + column.getValue();
            elements += column.getValue() * i;
        }
        for (DiagramColumnResponse column : columns) {
            column.setPercent(getPercent(column.getValue(), sum));
        }

        return new ColumnDiagramDataResponse(sum, 0, Math.round((elements / sum) * 10) / 10.0, columns);
    }


    public ColumnDiagramDataResponse getAdvertisementFeedbacksByMonthAndYear(Long id, int month, int year) {
        List<DiagramColumnResponse> columns = new ArrayList<>();
        int sum = 0;
        double elements = 0;
        for (int i = 5; i >= 1; i--) {
            DiagramColumnResponse column = new DiagramColumnResponse();
            column.setShowable(true);
            column.setValue(goodsAdvertisementFeedbackRepository.getCountOfFeedbacksByGoodsAdvertisementAndRatingAndMonthAndYear(id,i,month, year));
            columns.add(column);
            sum = sum + column.getValue();
            elements += column.getValue() * i;
        }
        for (DiagramColumnResponse column : columns) {
            column.setPercent(getPercent(column.getValue(), sum));
        }

        return new ColumnDiagramDataResponse(sum, 0, Math.round((elements / sum) * 10) / 10.0, columns);
    }

    public ColumnDiagramDataResponse getAdvertisementFeedbacksByYear(Long id, int year) {
        List<DiagramColumnResponse> columns = new ArrayList<>();
        int sum = 0;
        for (int i = 5; i >= 1; i--) {
            DiagramColumnResponse column = new DiagramColumnResponse();
            column.setShowable(true);
            column.setValue(goodsAdvertisementFeedbackRepository.getCountOfFeedbacksByGoodsAdvertisementAndRatingAndYear(id, i, year));
            columns.add(column);
            sum = sum + column.getValue();
        }
        for (DiagramColumnResponse column : columns) {
            column.setPercent(getPercent(column.getValue(), sum));
        }

        return new ColumnDiagramDataResponse(sum, 0, goodsAdvertisementFeedbackRepository.getAverageRatingByGoodsAdvertisementAndRatingAndYear(id, year).orElse(0.0) , columns);
    }


    public ColumnDiagramDataResponse getAdvertisementFeedbacks(Long id) {
        List<DiagramColumnResponse> columns = new ArrayList<>();
        int sum = 0;
        for (int i = 5; i >= 1; i--) {
            DiagramColumnResponse column = new DiagramColumnResponse();
            column.setShowable(true);
            column.setValue(goodsAdvertisementFeedbackRepository.getCountOfFeedbacksByGoodsAdvertisementAndRating(i, id));
            columns.add(column);
            sum = sum + column.getValue();
        }
        for (DiagramColumnResponse column : columns) {
            column.setPercent(getPercent(column.getValue(), sum));
        }
        return new ColumnDiagramDataResponse(sum, 0, goodsAdvertisementFeedbackRepository.getAverageRatingByGoodsAdvertisement(id).orElse(0.0), columns);
    }

    private boolean canShowStatistics(int year, int month, GoodsAdvertisement advertisement) {
        LocalDateTime now =  LocalDateTime.now();
        LocalDateTime creation =  advertisement.getCreationDate();

        if (year == now.getYear() && month > now.getMonth().getValue()) return false;
        else if (year == creation.getYear() && month < creation.getMonth().getValue()) return false;
        else return year >= creation.getYear() && year <= now.getYear();
    }

    private int getPercent(int value, int max) {
        if (value == 0) return 0;
        int p = (int) Math.round(value * 100.0 / max);
        if (p == 0) return 1;
        else return p;
    }


    private int getMaxDiagramValueByMaxColumnValue(int maxValue) {
        if (maxValue <= 10) {
            return 10;
        } else if (maxValue < 50) {
            return ((maxValue + 9) / 10) * 10;
        }  else if (maxValue < 5000) {
            return ((maxValue + 99) / 100) * 100;
        } else {
            return ((maxValue + 999) / 1000) * 1000;
        }
    }


    public GoodsAdvertisementStatisticsResponse getAdvertisementStaticsResponse(Long id) {
        GoodsAdvertisement advertisement = goodsAdvertisementService.findById(id);
        return new GoodsAdvertisementStatisticsResponse();
    }

    public String getAdvertisementCreationDate(Long id) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return goodsAdvertisementService.findById(id).getCreationDate().format(formatter);
    }

}

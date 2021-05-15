package rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.discount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.discount.DiscountRequest;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.discounts.CheckDiscountCreatingResponse;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.discounts.DiscountResponse;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.discount.Discount;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.discount.DiscountType;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.parameters.ParametersValuesPriceCount;
import rostyk.stupnytskiy.andromeda.repository.advertisement.goods_advertisement.discount.DiscountRepository;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.GoodsAdvertisementService;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.parameter.ParameterService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private ParameterService parameterService;

    @Autowired
    private GoodsAdvertisementService goodsAdvertisementService;

    public void saveDiscount(DiscountRequest discountRequest) {
        Discount discount = discountRequestToDiscount(discountRequest);
        validateDiscount(discount);
        discountRepository.save(discount);
    }


    private void validateDiscount(Discount discount) {
        if (discount.getForAllParameters()) {
            for (ParametersValuesPriceCount valuesPriceCount : discount.getGoodsAdvertisement().getValuesPriceCounts()) {
                validateForParametersValuePriceCount(discount, valuesPriceCount);
            }
        } else {
            validateForParametersValuePriceCount(discount, null);
        }
    }

    private void validateForParametersValuePriceCount(Discount discount, ParametersValuesPriceCount valuesPriceCount) {
        if (valuesPriceCount == null) valuesPriceCount = discount.getValuesPriceCount();

        if (
                discountRepository.existsByParamsValueIdAndStartDateAndEndDateBetween(valuesPriceCount.getId(), discount.getStartDate()) ||
                        discountRepository.existsByParamsValueIdAndStartDateAndEndDateBetween(valuesPriceCount.getId(), discount.getEndDate()) ||
                        discountRepository.existsByParamsValueIdAndStartDateOrEndDateBetweenDates(valuesPriceCount.getId(), discount.getStartDate(), discount.getEndDate())
        ) throw new IllegalArgumentException();
        if (
                discount.getDiscountType() == DiscountType.DISCOUNT_PERCENT &&
                        (discount.getDiscountValue() > 99)
        ) throw new IllegalArgumentException();
    }

    private Discount discountRequestToDiscount(DiscountRequest request) {
        Discount discount = new Discount();
        discount.setDiscountType(request.getDiscountType());
        discount.setDiscountValue(request.getDiscountValue());

        discount.setValuesPriceCount(parameterService.findParametersValuesPriceCountById(request.getValuesPriceCountId()));
        discount.setGoodsAdvertisement(discount.getValuesPriceCount().getGoodsAdvertisement());

        discount.setForAllParameters(request.getForAllParameters());

        if (request.getStartDate() == null) {
            discount.setStartDate(LocalDateTime.now());
        } else {
            discount.setStartDate(
                    LocalDateTime.of(LocalDate.parse(request.getStartDate()), LocalTime.of(0, 0, 0))
            );
        }

        if (request.getEndDate() != null && !request.getEndDate().equals("")) {
            discount.setEndDate(
                    LocalDateTime.of(LocalDate.parse(request.getEndDate()), LocalTime.of(23, 59, 59))
            );
        } else {
            discount.setEndDate(LocalDateTime.of(2027, 5, 23, 23, 59, 59));
        }

        return discount;
    }

    public CheckDiscountCreatingResponse checkDiscountForCreating(DiscountRequest request) {
        Discount discount = discountRequestToDiscount(request);
        CheckDiscountCreatingResponse response = new CheckDiscountCreatingResponse();
        List<Discount> conflicts = discountRepository.findConflictDiscounts(discount.getValuesPriceCount().getId(), discount.getStartDate(), discount.getEndDate());
        response.setCanCreate(conflicts.size() == 0);
        if (!response.getCanCreate()) {
            response.setConflictDiscount(new DiscountResponse(conflicts.get(0)));
        }

        return response;
    }

    public Discount findById(Long id) {
        return discountRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public boolean closeDiscount(Long id) {
        Discount discount = findById(id);
        if (!discount.isActive()) return false;
        else {
            discount.setEndDate(LocalDateTime.now());
            discountRepository.save(discount);
            return true;
        }
    }

    public void deleteDiscount(Long id) {
        Discount discount = findById(id);
        if (!discount.isActive()) discountRepository.delete(findById(id));
    }
}

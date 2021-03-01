package rostyk.stupnytskiy.andromeda.controller.advertisement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.discount.DiscountRequest;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.discounts.CheckDiscountCreatingResponse;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.discounts.DiscountsForParametersValuesPriceCountResponse;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.discount.DiscountService;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.parameter.ParameterService;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/discount")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @Autowired
    private ParameterService parameterService;

    @PostMapping
    private void createDiscount(@RequestBody @Valid DiscountRequest request){
        discountService.saveDiscount(request);
    }

    @GetMapping("parameters")
    private List<DiscountsForParametersValuesPriceCountResponse> getParametersValuesPriceCountWithDiscounts(Long id) {
        return parameterService.getParametersValuesPriceCountResponseWithDiscount(id);
    }

    @PutMapping("check")
    private CheckDiscountCreatingResponse checkDiscountForCreating(@RequestBody @Valid DiscountRequest request) {
        return discountService.checkDiscountForCreating(request);
    }

    @PutMapping("close")
    private boolean closeDiscount(Long id) {
        return discountService.closeDiscount(id);
    }

    @DeleteMapping
    private void delete(Long id) {
        discountService.deleteDiscount(id);
    }
}

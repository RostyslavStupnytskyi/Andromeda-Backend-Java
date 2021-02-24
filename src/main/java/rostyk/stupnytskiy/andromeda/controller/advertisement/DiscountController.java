package rostyk.stupnytskiy.andromeda.controller.advertisement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.discount.DiscountRequest;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.discount.DiscountService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@CrossOrigin
@RestController
@RequestMapping("/discount")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @PostMapping
    private void createDiscount(@RequestBody @Valid DiscountRequest request){
        discountService.saveDiscount(request);
    }
}

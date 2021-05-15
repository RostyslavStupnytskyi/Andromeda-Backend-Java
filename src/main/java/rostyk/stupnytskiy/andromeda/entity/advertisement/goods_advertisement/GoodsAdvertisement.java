package rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement;

import lombok.*;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;
import rostyk.stupnytskiy.andromeda.entity.country.Currency;
import rostyk.stupnytskiy.andromeda.entity.country.DeliveryType;
import rostyk.stupnytskiy.andromeda.entity.Subcategory;
import rostyk.stupnytskiy.andromeda.entity.account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.account.goods_seller.categories.GoodsSellerAdvertisementCategory;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
import rostyk.stupnytskiy.andromeda.entity.advertisement.Advertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.discount.Discount;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.parameters.Parameter;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.parameters.ParametersValuesPriceCount;
import rostyk.stupnytskiy.andromeda.entity.feedback.GoodsAdvertisementFeedback;
import rostyk.stupnytskiy.andromeda.entity.order.order_item.GoodsOrderItem;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@DiscriminatorValue("goods_advertisement")
public class GoodsAdvertisement extends Advertisement {

    private Boolean onlySellerCountry;

    private Double priceToSort;

    private Boolean hasParameters;

    private LocalDateTime creationDate;

    @OneToMany(mappedBy = "goodsAdvertisement", fetch = FetchType.LAZY)
    private List<Parameter> parameters;

    @OneToMany(mappedBy = "goodsAdvertisement", fetch = FetchType.LAZY)
    private List<ParametersValuesPriceCount> valuesPriceCounts;

    @ManyToOne(fetch = FetchType.LAZY)
    private Subcategory subcategory;

    @ElementCollection
    private List<String> images = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private GoodsSellerAccount seller;

    @OneToMany(mappedBy = "advertisement", fetch = FetchType.LAZY)
    private List<Property> properties = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    private List<DeliveryType> deliveryTypes = new ArrayList<>();

    @OneToMany(mappedBy = "goodsAdvertisement", fetch = FetchType.LAZY)
    private List<GoodsAdvertisementFeedback> feedbacks;

    @ManyToMany(mappedBy = "favoriteAdvertisements", fetch = FetchType.LAZY)
    private List<UserAccount> users;

    @OneToMany(mappedBy = "goodsAdvertisement", fetch = FetchType.LAZY)
    private List<Discount> discounts;

    @ManyToOne
    private GoodsSellerAdvertisementCategory sellerCategory;

    @OneToMany(mappedBy = "goodsAdvertisement", fetch = FetchType.LAZY)
    private List<GoodsOrderItem> orderItems = new ArrayList<>();

// Formula("Select round(avg(f.rating),1) from goods_advertisement_feedback f where f.goods_advertisement_id = id")

    @Override
    public String toString() {
        return "GoodsAdvertisement{ id = " + this.getId() + ", seller = " + this.getSeller().toString() + "}";
    }

    public Double getMaxPrice(Currency currency) {
        return valuesPriceCounts
                .stream()
                .mapToDouble((p) -> p.getPriceByCurrency(currency))
                .max()
                .getAsDouble();
    }

    public Double getMinPrice(Currency currency) {
        return valuesPriceCounts
                .stream()
                .mapToDouble((p) -> p.getPriceByCurrency(currency))
                .min()
                .getAsDouble();
    }

    public Double getMaxPriceWithDiscounts(Currency currency) {
        return valuesPriceCounts
                .stream()
                .mapToDouble((p) -> p.getPriceWithCurrentDiscount(currency))
                .max()
                .getAsDouble();
    }

    public Double getMinPriceWithDiscounts(Currency currency) {
        return valuesPriceCounts
                .stream()
                .mapToDouble((p) -> p.getPriceWithCurrentDiscount(currency))
                .min()
                .getAsDouble();
    }

    public boolean hasDiscount() {
        for (ParametersValuesPriceCount valuesPriceCount : valuesPriceCounts)
            if (valuesPriceCount.hasDiscount()) return true;
        return false;
    }

    public boolean hasCurrency(Currency currency) {
        for (ParametersValuesPriceCount valuesPriceCount : valuesPriceCounts)
            if (valuesPriceCount.hasCurrency(currency)) return true;
        return false;
    }

    public boolean hasCurrency(String code) {
        for (ParametersValuesPriceCount valuesPriceCount : valuesPriceCounts)
            if (valuesPriceCount.hasCurrency(code)) return true;
        return false;
    }

    public int getCountOrdersByYearAndMonth(int year, Month month) {
        int count = 0;
        for (GoodsOrderItem orderItem : orderItems) {
            if (orderItem.getGoodsOrder().getCreationDate().getMonth() == month &&
                    orderItem.getGoodsOrder().getCreationDate().getYear() == year) count++;
        }
        return count;
    }

}


package rostyk.stupnytskiy.andromeda.specification;

import org.springframework.data.jpa.domain.Specification;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.GoodsAdvertisementSearchRequest;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisementStatistics;
import rostyk.stupnytskiy.andromeda.entity.country.Country;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class GoodsAdvertisementSpecification implements Specification<GoodsAdvertisement> {

    private String title;
    private String fromCountryCode;
    private Boolean image;
    private Boolean rating;



    public GoodsAdvertisementSpecification(GoodsAdvertisementSearchRequest request) {
        this.title = request.getTitle();
        this.fromCountryCode = request.getFromCountryCode();
        this.image = request.getImage();
        this.rating = request.getRating();
    }

    @Override
    public Predicate toPredicate(Root<GoodsAdvertisement> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        final List<Predicate> predicates = new ArrayList<>();
        predicates.add(findByTitleLike(root, cb));
        predicates.add(findByCountry(root, cb));
        predicates.add(findByRating(root, cb));
        predicates.add(findByImage(root, cb));
        return cb.and(predicates.toArray(new Predicate[0]));
    }

    private Predicate findByTitleLike(Root<GoodsAdvertisement> r, CriteriaBuilder cb) {
        Predicate predicate;
        if (title == null || title.trim().isEmpty()) {
            predicate = cb.conjunction();
        } else {
            predicate = cb.like(r.get("title"), '%' + title + '%');
        }
        return predicate;
    }

    private Predicate findByImage(Root<GoodsAdvertisement> r, CriteriaBuilder cb) {
        Predicate predicate;
        if (image != null && image) {
            return cb.isNotNull(r.get("mainImage"));
        } else {
            predicate = cb.conjunction();
        }
        return predicate;
    }

    private Predicate findByRating(Root<GoodsAdvertisement> r, CriteriaBuilder cb) {
        Predicate predicate;


        if (rating != null && rating) {
            final Join<GoodsAdvertisement, GoodsAdvertisementStatistics> statisticsJoin  = r.join("statistics");
            return cb.greaterThanOrEqualTo(statisticsJoin.get("rating"), 4.0);
        } else {
            predicate = cb.conjunction();
        }
        return predicate;
    }

    private Predicate findByCountry(Root<GoodsAdvertisement> r, CriteriaBuilder cb) {
        Predicate predicate;

        final Join<GoodsAdvertisement, GoodsSellerAccount> sellerJoin  = r.join("seller");
        final Join<GoodsSellerAccount, Country> countryJoin = sellerJoin.join("country");
        if (fromCountryCode != null) {
            return cb.equal(countryJoin.get("countryCode"), fromCountryCode);
        } else {
            predicate = cb.conjunction();
        }
        return predicate;
    }




}

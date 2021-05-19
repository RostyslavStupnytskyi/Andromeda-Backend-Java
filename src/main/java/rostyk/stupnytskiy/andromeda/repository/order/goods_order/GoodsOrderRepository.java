package rostyk.stupnytskiy.andromeda.repository.order.goods_order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrder;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrderStatus;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface GoodsOrderRepository extends JpaRepository<GoodsOrder, Long> {

    Optional<GoodsOrder> findOneByIdAndSeller(Long id, GoodsSellerAccount seller);

    Optional<GoodsOrder> findOneByIdAndUser(Long id, UserAccount user);

    Page<GoodsOrder> findPageBySeller(GoodsSellerAccount goodsSellerAccount, Pageable pageable);

    Page<GoodsOrder> findPageByUser(UserAccount user, Pageable pageable);

    Page<GoodsOrder> findPageBySellerLoginAndOrderStatus(String login, GoodsOrderStatus status, Pageable pageable);

    Page<GoodsOrder> findPageBySellerAndOrderStatus(GoodsSellerAccount seller, GoodsOrderStatus status, Pageable pageable);

    Page<GoodsOrder> findPageByUserAndOrderStatus(UserAccount user, GoodsOrderStatus orderStatus, Pageable pageable);

    Page<GoodsOrder> findPageByUserAndOrderStatusOrOrderStatus(UserAccount user, GoodsOrderStatus orderStatus1, GoodsOrderStatus orderStatus2, Pageable pageable);

    Page<GoodsOrder> findPageBySellerAndOrderStatusOrOrderStatus(GoodsSellerAccount sellerAccount, GoodsOrderStatus orderStatus1, GoodsOrderStatus orderStatus2, Pageable pageable);

    Page<GoodsOrder> findAllBySellerAndOrderStatusIn(GoodsSellerAccount sellerAccount, Set<GoodsOrderStatus> orderStatus, Pageable pageable);

    List<GoodsOrder> findAllByUserAndOrderStatus(UserAccount user, GoodsOrderStatus orderStatus);

    List<GoodsOrder> findAllBySellerAndOrderStatus(GoodsSellerAccount seller, GoodsOrderStatus orderStatus);

    Page<GoodsOrder> findPageBySellerAndIsViewedIsFalse(GoodsSellerAccount seller, Pageable pageable);

    List<GoodsOrder> findAllBySellerAndIsViewedIsFalse(GoodsSellerAccount seller);

    List<GoodsOrder> findAllByUser(UserAccount user);

    List<GoodsOrder> findAllBySeller(GoodsSellerAccount seller);


    @Query("Select distinct o from GoodsOrder o where o.seller = :seller and o.orderStatus in :statuses")
    Page<GoodsOrder> findPageBySellerAndOrderStatusIn(
            @Param("seller") GoodsSellerAccount seller,
            @Param("statuses") GoodsOrderStatus[] statuses,
            Pageable pageable);

    @Query("Select distinct o from GoodsOrder o where o.user = :user and o.orderStatus in :statuses")
    Page<GoodsOrder> findPageByUserAndOrderStatusIn(
            @Param("user") UserAccount user,
            @Param("statuses") GoodsOrderStatus[] statuses,
            Pageable mapToPageable);
}

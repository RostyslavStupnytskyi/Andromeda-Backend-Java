package rostyk.stupnytskiy.andromeda.repository.advertisement.goods_advertisement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.Property;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findAllByAdvertisementId(Long id);
    Page<Property> findAllByAdvertisementId(Long id, Pageable pageable);
}

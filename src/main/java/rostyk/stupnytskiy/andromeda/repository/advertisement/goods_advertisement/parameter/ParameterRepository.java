package rostyk.stupnytskiy.andromeda.repository.advertisement.goods_advertisement.parameter;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.parameters.Parameter;

import java.util.Optional;


@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Long>{

    Optional<Parameter> findOneByTitleAndGoodsAdvertisementId(String title, Long id);

}

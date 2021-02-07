package rostyk.stupnytskiy.andromeda.repository.advertisement.goods_advertisement.parameter;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.parameters.Parameter;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.parameters.ParameterValue;

import java.util.Optional;


@Repository
public interface ParameterValueRepository extends JpaRepository<ParameterValue, Long>{

    Optional<ParameterValue> findOneByTitleAndParameter(String title, Parameter parameter);
}

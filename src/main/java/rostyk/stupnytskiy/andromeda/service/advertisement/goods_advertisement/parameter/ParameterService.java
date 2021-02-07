package rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.parameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.repository.advertisement.goods_advertisement.parameter.ParameterRepository;
import rostyk.stupnytskiy.andromeda.repository.advertisement.goods_advertisement.parameter.ParameterValueRepository;
import rostyk.stupnytskiy.andromeda.repository.advertisement.goods_advertisement.parameter.ParametersValuePriceCountRepository;

@Service
public class ParameterService {

    @Autowired
    private ParameterRepository parameterRepository;

    @Autowired
    private ParameterValueRepository parameterValueRepository;

    @Autowired
    private ParametersValuePriceCountRepository parametersValuePriceCountRepository;


}

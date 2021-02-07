package rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.parameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.parameter.ParameterRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.parameter.ParameterValueRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.parameter.ParametersValuesPriceCountRequest;
import rostyk.stupnytskiy.andromeda.entity.Category;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.parameters.Parameter;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.parameters.ParameterValue;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.parameters.ParametersValuesPriceCount;
import rostyk.stupnytskiy.andromeda.repository.advertisement.goods_advertisement.parameter.ParameterRepository;
import rostyk.stupnytskiy.andromeda.repository.advertisement.goods_advertisement.parameter.ParameterValueRepository;
import rostyk.stupnytskiy.andromeda.repository.advertisement.goods_advertisement.parameter.ParametersValuePriceCountRepository;
import rostyk.stupnytskiy.andromeda.tools.FileTool;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class ParameterService {

    @Autowired
    private ParameterRepository parameterRepository;

    @Autowired
    private ParameterValueRepository parameterValueRepository;

    @Autowired
    private ParametersValuePriceCountRepository parametersValuePriceCountRepository;

    @Autowired
    private FileTool fileTool;


    public void saveParameter(ParameterRequest request, GoodsAdvertisement goodsAdvertisement) {
        Parameter parameter = parameterRepository.save(parameterRequestToParameter(request, goodsAdvertisement));

        request.getValues().forEach((pv) -> saveParameterValue(pv, parameter));
    }

    public void saveParametersValuePriceCount(ParametersValuesPriceCountRequest request, GoodsAdvertisement goodsAdvertisement) {
        parametersValuePriceCountRepository.save(parametersValuePriceCountRequestToParametersValuePriceCount(request, goodsAdvertisement));
    }

    private ParametersValuesPriceCount parametersValuePriceCountRequestToParametersValuePriceCount(ParametersValuesPriceCountRequest request, GoodsAdvertisement goodsAdvertisement) {
        ParametersValuesPriceCount parametersValuesPriceCount = new ParametersValuesPriceCount();
        parametersValuesPriceCount.setCount(request.getCount());
        parametersValuesPriceCount.setPrice(request.getPrice());
        parametersValuesPriceCount.setGoodsAdvertisement(goodsAdvertisement);

        Map<Parameter, ParameterValue> parameterValueMap = new HashMap<>();
        Parameter parameter;
        ParameterValue parameterValue;

        for (Map.Entry<String, String> paramValue : request.getValueParam().entrySet()) {
            parameter = findParameterByTitleAndAdvertisementId(paramValue.getKey(), goodsAdvertisement);
            parameterValue = findParameterValueByTitleAndParameter(paramValue.getValue(), parameter);
            parameterValueMap.put(parameter, parameterValue);
        }
        parametersValuesPriceCount.setValues(parameterValueMap);

        return parametersValuesPriceCount;
//        parameterValueMap.put();
//        parametersValuesPriceCount.set
    }

    private Parameter findParameterByTitleAndAdvertisementId(String title, GoodsAdvertisement goodsAdvertisement) {
        return parameterRepository.findOneByTitleAndGoodsAdvertisementId(title, goodsAdvertisement.getId()).orElseThrow(IllegalArgumentException::new);
    }

    private ParameterValue findParameterValueByTitleAndParameter(String title, Parameter parameter) {
        return parameterValueRepository.findOneByTitleAndParameter(title, parameter).orElseThrow(IllegalArgumentException::new);
    }

    private Parameter parameterRequestToParameter(ParameterRequest request, GoodsAdvertisement goodsAdvertisement) {
        Parameter parameter = new Parameter();
        parameter.setTitle(request.getTitle());
        parameter.setPriceDependence(request.getPriceDependence());
        parameter.setGoodsAdvertisement(goodsAdvertisement);
        return parameter;
    }

    private ParameterValue saveParameterValue(ParameterValueRequest request, Parameter parameter) {
        return parameterValueRepository.save(parameterValueRequestToParameterValue(request, parameter));
    }

    private ParameterValue parameterValueRequestToParameterValue(ParameterValueRequest request, Parameter parameter) {
        ParameterValue parameterValue = new ParameterValue();

        parameterValue.setTitle(request.getTitle());

        if (request.getImage() != null) {
            try {
                parameterValue.setImage(fileTool.saveAdvertisementImage(request.getImage(), parameter.getGoodsAdvertisement().getSeller().getId()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        parameterValue.setParameter(parameter);

        return parameterValue;
    }

}

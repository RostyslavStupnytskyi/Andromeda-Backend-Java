package rostyk.stupnytskiy.andromeda.entity.advertisement;


import rostyk.stupnytskiy.andromeda.dto.response.advertisement.AdvertisementResponse;

public interface AdvertisementEntity {
    <T extends AdvertisementResponse> AdvertisementResponse mapToResponse();


}

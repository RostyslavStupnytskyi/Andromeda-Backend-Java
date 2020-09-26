package rostyk.stupnytskiy.andromeda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.AdvertisementRequest;
import rostyk.stupnytskiy.andromeda.entity.Account;
import rostyk.stupnytskiy.andromeda.entity.Advertisement;
import rostyk.stupnytskiy.andromeda.repository.AdvertisementRepository;
import rostyk.stupnytskiy.andromeda.tools.FileTool;

import java.io.IOException;

@Service
public class AdvertisementService {
    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private SubcategoryService subcategoryService;

    @Autowired
    private FileTool fileTool;

    public void save(AdvertisementRequest request) throws IOException {
        advertisementRepository.save(advertisementRequestToAdvertisement(request,null));
    }

    public void update(AdvertisementRequest request, Long id) throws IOException {
        advertisementRepository.save(advertisementRequestToAdvertisement(request,findById(id)));
    }

    public void delete(Long id){
        advertisementRepository.delete(findById(id));
    }

    public Advertisement findById(Long id) {
        return advertisementRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Advertisement with id " + id + " does not exists"));
    }

    private Advertisement advertisementRequestToAdvertisement(AdvertisementRequest request, Advertisement advertisement) throws IOException {
        if (advertisement == null) {
            advertisement = new Advertisement();
        }

        Account account = accountService.findByLogin((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        advertisement.setTitle(request.getTitle());
        advertisement.setPrice(request.getPrice());
        advertisement.setDescription(request.getDescription());
        advertisement.setSeller(account.getSeller());
        advertisement.setSubcategory(subcategoryService.findOneById(request.getSubcategoryId()));

        if (request.getMainImage() != null) {
            advertisement.setMainImage(fileTool.savePublicationImage(request.getMainImage(), account.getLogin()));
        }

        return advertisement;
    }
}

package rostyk.stupnytskiy.andromeda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.PaginationRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.AdvertisementRequest;
import rostyk.stupnytskiy.andromeda.dto.response.AdvertisementResponse;
import rostyk.stupnytskiy.andromeda.dto.response.PageResponse;
import rostyk.stupnytskiy.andromeda.entity.account.Account;
import rostyk.stupnytskiy.andromeda.entity.advertisement.Advertisement;
import rostyk.stupnytskiy.andromeda.repository.AdvertisementRepository;
import rostyk.stupnytskiy.andromeda.tools.FileTool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public Long save(AdvertisementRequest request) throws IOException {
//        System.out.println(request.print());
        Long id = advertisementRepository.save(advertisementRequestToAdvertisement(request, null)).getId();
        saveAdvertisementImages(request, id);
        return id;
    }

    public void update(AdvertisementRequest request, Long id) throws IOException {
        advertisementRepository.save(advertisementRequestToAdvertisement(request, findById(id)));
    }

    public void delete(Long id) {
        advertisementRepository.delete(findById(id));
    }

    public Advertisement findById(Long id) {
        return advertisementRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Advertisement with id " + id + " does not exists"));
    }

    public PageResponse<AdvertisementResponse> findPage(PaginationRequest request) {
        final Page<Advertisement> page = advertisementRepository.findAll(request.mapToPageable());
        return new PageResponse<>(page.getContent().stream().map(AdvertisementResponse::new).collect(Collectors.toList()),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    private Advertisement advertisementRequestToAdvertisement(AdvertisementRequest request, Advertisement advertisement) {
        if (advertisement == null) {
            advertisement = new Advertisement();
       //   advertisement.setCreationDate(LocalDateTime.now()); TODO
        }

        Account account = accountService.findByLogin((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        advertisement.setTitle(request.getTitle());
        advertisement.setDescription(request.getDescription());
        advertisement.setSeller(account.getSeller());
        advertisement.setSubcategory(subcategoryService.findOneById(request.getSubcategoryId()));
        advertisement.setImages(new ArrayList<>());

        return advertisement;
    }

    private void saveAdvertisementImages(AdvertisementRequest request, Long id) throws IOException {
        Advertisement advertisement = findById(id);
        Account account = accountService.findByLogin((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if (request.getMainImage() != null) {
            advertisement.setMainImage(fileTool.savePublicationImage(request.getMainImage(), account.getLogin(), id));
        }
        List<String> images = new ArrayList<>();
        for (String image : request.getImages()) {
            images.add(fileTool.savePublicationImage(image, account.getLogin(), id));
        }
        advertisement.setImages(images);
        advertisementRepository.save(advertisement);
    }
}

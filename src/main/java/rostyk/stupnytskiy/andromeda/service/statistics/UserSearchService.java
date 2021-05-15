package rostyk.stupnytskiy.andromeda.service.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.entity.statistics.UserSearch;
import rostyk.stupnytskiy.andromeda.repository.statistics.UserSearchRepository;
import rostyk.stupnytskiy.andromeda.service.account.UserAccountService;

@Service
public class UserSearchService {

    @Autowired
    private UserSearchRepository userSearchRepository;

    @Autowired
    private UserAccountService userAccountService;

    public void saveSearch(String value) {
        UserSearch search = new UserSearch();
        search.setUser(userAccountService.findBySecurityContextHolderOrReturnNull());
        search.setValue(value);
        userSearchRepository.save(search);
    }
}

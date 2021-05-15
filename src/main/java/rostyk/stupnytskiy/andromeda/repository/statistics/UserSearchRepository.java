package rostyk.stupnytskiy.andromeda.repository.statistics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.statistics.UserSearch;

@Repository
public interface UserSearchRepository extends JpaRepository<UserSearch,Long> {

}

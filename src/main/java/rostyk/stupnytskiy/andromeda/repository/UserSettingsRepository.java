package rostyk.stupnytskiy.andromeda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserSettings;

@Repository
public interface UserSettingsRepository extends JpaRepository<UserSettings, Long> {
}

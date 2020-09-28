package rostyk.stupnytskiy.andromeda.mail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MailDataRepository extends JpaRepository<MailData,Long> {
    Boolean existsMailDataByMailRole(MailRole mailRole);
    Optional<MailData> findOneByMailRole(MailRole mailRole);
}

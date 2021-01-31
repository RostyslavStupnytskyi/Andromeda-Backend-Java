package rostyk.stupnytskiy.andromeda.repository;

import org.aspectj.weaver.ast.Not;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rostyk.stupnytskiy.andromeda.entity.account.Account;
import rostyk.stupnytskiy.andromeda.entity.notification.Notification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;


@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {

    Page<Notification> findPageByReceiverOrderByIdDesc(Account account, Pageable pageable);
    Page<Notification> findPageByReceiverAndDate(Account account, Pageable pageable, LocalDate date);

    Optional<Notification> findByReceiverAndId(Account account, Long id);
}

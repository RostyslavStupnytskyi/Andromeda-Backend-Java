package rostyk.stupnytskiy.andromeda.repository.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.account.Account;
import rostyk.stupnytskiy.andromeda.entity.message.Chat;
import rostyk.stupnytskiy.andromeda.entity.message.Message;

import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query(value = "select * from chat c where c.id = (select ac.chats_id from account_chats ac where ac.chats_id = :id and ac.members_id = :member)",
            nativeQuery = true)
    Optional<Chat> findByIdAndMember(
            @Param("id") Long id,
            @Param("member") Long memberId);
}

package rostyk.stupnytskiy.andromeda.repository.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rostyk.stupnytskiy.andromeda.entity.message.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {

}

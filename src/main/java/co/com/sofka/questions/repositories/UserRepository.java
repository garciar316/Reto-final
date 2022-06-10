package co.com.sofka.questions.repositories;

import co.com.sofka.questions.collections.User;
import co.com.sofka.questions.model.UserDTO;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, String> {

}

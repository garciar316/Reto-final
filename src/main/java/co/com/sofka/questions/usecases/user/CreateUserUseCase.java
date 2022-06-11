package co.com.sofka.questions.usecases.user;

import co.com.sofka.questions.collections.User;
import co.com.sofka.questions.mappers.UserMapper;
import co.com.sofka.questions.model.UserDTO;
import co.com.sofka.questions.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public CreateUserUseCase(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public Mono<String> apply (UserDTO userDTO){
        System.out.println("CreateUserUseCase-->"+ userDTO.toString());
        return userRepository.findById(userDTO.getId())
                .switchIfEmpty(userRepository.save(userMapper.userDTOToUser(userDTO.getId()).apply(userDTO)))
                .map(User::getId);
    }
}

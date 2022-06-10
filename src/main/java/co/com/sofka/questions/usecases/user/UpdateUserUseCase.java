package co.com.sofka.questions.usecases.user;

import co.com.sofka.questions.mappers.UserMapper;
import co.com.sofka.questions.model.UserDTO;
import co.com.sofka.questions.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class UpdateUserUseCase {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UpdateUserUseCase(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public Mono<UserDTO> apply (UserDTO userDto){
        return  userRepository.save(userMapper.userDTOToUser(userDto.getId()).apply(userDto))
                .map(user -> userMapper.userToUserDTO().apply(user));
    }

}

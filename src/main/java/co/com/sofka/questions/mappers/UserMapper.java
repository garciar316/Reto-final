package co.com.sofka.questions.mappers;

import co.com.sofka.questions.collections.User;
import co.com.sofka.questions.model.UserDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserMapper {
    public Function<User, UserDTO> userToUserDTO() {
        return entity -> new UserDTO(
                entity.getId(),
                entity.getName(),
                entity.getEmail()
        );
    }

    public Function<UserDTO, User> userDTOToUser(String id) {
        return updateUser -> {
            var user = new User(
                    id,
                    updateUser.getName(),
                    updateUser.getEmail()
            );
            return user;
        };
    }
}

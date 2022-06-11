package co.com.sofka.questions.routers;

import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.model.UserDTO;
import co.com.sofka.questions.usecases.*;
import co.com.sofka.questions.usecases.answer.AddAnswerUseCase;
import co.com.sofka.questions.usecases.answer.DeleteAnswerUseCase;
import co.com.sofka.questions.usecases.questions.*;
import co.com.sofka.questions.usecases.user.CreateUserUseCase;
import co.com.sofka.questions.usecases.user.FindUserByIdUseCase;
import co.com.sofka.questions.usecases.user.UpdateUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class QuestionRouter {

    @Bean
    public RouterFunction<ServerResponse> getAll(ListUseCase listUseCase) {
        return route(GET("/getAll"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(listUseCase.get(), QuestionDTO.class))
                        .onErrorResume(throwable -> ServerResponse.badRequest().body(throwable.getMessage(), String.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> getOwnerAll(OwnerListUseCase ownerListUseCase) {
        return route(
                GET("/getOwnerAll/{userId}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(
                                ownerListUseCase.apply(request.pathVariable("userId")),
                                QuestionDTO.class
                         ))
                        .onErrorResume(throwable -> ServerResponse.badRequest().body(throwable.getMessage(), String.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> create(CreateUseCase createUseCase) {
        Function<QuestionDTO, Mono<ServerResponse>> executor = questionDTO ->  createUseCase.apply(questionDTO)
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue(result))
                .onErrorResume(throwable -> ServerResponse.badRequest().body(throwable.getMessage(), String.class));

        return route(
                POST("/create").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(QuestionDTO.class).flatMap(executor)
                        .onErrorResume(throwable -> ServerResponse.badRequest().body(throwable.getMessage(), String.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> get(GetUseCase getUseCase) {
        return route(
                GET("/get/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getUseCase.apply(
                                request.pathVariable("id")),
                                QuestionDTO.class
                        ))
                        .onErrorResume(throwable -> ServerResponse.badRequest().body(throwable.getMessage(), String.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> addAnswer(AddAnswerUseCase addAnswerUseCase) {
        return route(POST("/add").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(AnswerDTO.class)
                        .flatMap(addAnswerDTO -> addAnswerUseCase.apply(addAnswerDTO)
                                .flatMap(result -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                        ).onErrorResume(throwable -> ServerResponse.badRequest().body(throwable.getMessage(), String.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> delete(DeleteUseCase deleteUseCase) {
        return route(
                DELETE("/delete/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(deleteUseCase.apply(request.pathVariable("id")), Void.class))
                        .onErrorResume(throwable -> ServerResponse.badRequest().body(throwable.getMessage(), String.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> deleteAnswer(DeleteAnswerUseCase deleteAnswerUseCase) {
        return route(
                DELETE("/deleteAnswer/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(deleteAnswerUseCase.apply(request.pathVariable("id")), Void.class))
                        .onErrorResume(throwable -> ServerResponse.badRequest().body(throwable.getMessage(), String.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> update(UpdateUseCase updateUseCase) {
        Function<QuestionDTO, Mono<ServerResponse>> executor = questionDTO ->  updateUseCase.apply(questionDTO)
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue(result));

        return route(
                PUT("/update").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(QuestionDTO.class).flatMap(executor)
                        .onErrorResume(throwable -> ServerResponse.badRequest().body(throwable.getMessage(), String.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> createUser(CreateUserUseCase createUserUseCase){
        return route(POST("/createUser").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(UserDTO.class)
                        .flatMap(userDto -> createUserUseCase.apply(userDto)
                                .flatMap(result -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                        ).onErrorResume(throwable -> ServerResponse.badRequest().body(throwable.getMessage(), String.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> updateUser(UpdateUserUseCase updateUserUseCase){
        return route(POST("/updateUser").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(UserDTO.class)
                        .flatMap(userDto -> updateUserUseCase.apply(userDto)
                                .flatMap(result -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                        ).onErrorResume(throwable -> ServerResponse.badRequest().body(throwable.getMessage(), String.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> findUser(FindUserByIdUseCase findUserByIdUseCase) {
        return route(
                GET("/user/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(findUserByIdUseCase.findUserById(request.pathVariable("id")), UserDTO.class))
                        .onErrorResume(throwable -> ServerResponse.badRequest().body(throwable.getMessage(), String.class))
        );
    }

}

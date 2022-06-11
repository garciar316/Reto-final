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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
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
    @RouterOperation(path = "/getAll", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = ListUseCase.class, method = RequestMethod.GET, beanMethod = "get",
            operation = @Operation(operationId = "get", responses = {
                    @ApiResponse(responseCode = "200",
                            content = @Content(schema = @Schema(implementation = QuestionDTO.class)))}
            ))
    public RouterFunction<ServerResponse> getAll(ListUseCase listUseCase) {
        return route(GET("/getAll"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(listUseCase.get(), QuestionDTO.class))
                        .onErrorResume(throwable -> ServerResponse.badRequest().body(throwable.getMessage(), String.class))
        );
    }

    @Bean
    @RouterOperation(path = "/getOwnerAll/{userId}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = OwnerListUseCase.class, method = RequestMethod.GET, beanMethod = "apply",
            operation = @Operation(operationId = "get", responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation",
                            content = @Content(schema = @Schema(implementation = QuestionDTO.class)))},
                    parameters = {@Parameter(in = ParameterIn.PATH, name = "userId")}
            ))
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
    @RouterOperation(path = "/create", produces = {
            MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST, beanClass = CreateUseCase.class, beanMethod = "apply",
            operation = @Operation(operationId = "apply", responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = QuestionDTO.class)))}
                    , requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = QuestionDTO.class)))
            ))
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
    @RouterOperation(path = "/get/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = OwnerListUseCase.class, method = RequestMethod.GET, beanMethod = "apply",
            operation = @Operation(operationId = "apply", responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation",
                            content = @Content(schema = @Schema(implementation = QuestionDTO.class)))},
                    parameters = {@Parameter(in = ParameterIn.PATH, name = "id")}
            ))
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
    @RouterOperation(path = "/add", produces = {
            MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST, beanClass = AddAnswerUseCase.class, beanMethod = "apply",
            operation = @Operation(operationId = "apply", responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = QuestionDTO.class)))}
                    , requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = AnswerDTO.class)))
            ))
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
    @RouterOperation(path = "/delete/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.DELETE, beanClass = DeleteUseCase.class, beanMethod = "apply"
            , operation = @Operation(operationId = "apply", responses = {
            @ApiResponse(responseCode = "200", description = "successful operation")},
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "id")}
    ))
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
    @RouterOperation(path = "/deleteAnswer/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.DELETE, beanClass = DeleteAnswerUseCase.class, beanMethod = "apply"
            , operation = @Operation(operationId = "apply", responses = {
            @ApiResponse(responseCode = "200", description = "successful operation")},
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "id")}
    ))
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
    @RouterOperation(path = "/update"
            , produces = {
            MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.PUT, beanClass = UpdateUserUseCase.class, beanMethod = "apply",
            operation = @Operation(operationId = "apply", responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "500", description = "Bad Request")},
                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = QuestionDTO.class))))
    )
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

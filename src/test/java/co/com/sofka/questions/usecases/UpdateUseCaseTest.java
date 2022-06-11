package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.repositories.QuestionRepository;
import co.com.sofka.questions.usecases.questions.UpdateUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest
class UpdateUseCaseTest {


    @SpyBean
    private UpdateUseCase updateUseCase;

    @MockBean
    private QuestionRepository repository;

    @Test
    void updateTest(){

        var resourceDT0 = new QuestionDTO("xxxx", "yyyy", "¿Test OK?", "OPEN", "TECHNOLOGY", "ss.rodriguez00021@gmai");

        var resource = new Question();
        resource.setId("xxxx");
        resource.setUserId("yyyy");
        resource.setQuestion("Que es Java?");
        resource.setType("tecnologia");
        resource.setCategory("TECNOLOGIA");

        when(repository.save(Mockito.any(Question.class))).thenReturn(Mono.just(resource));
        var result = updateUseCase.apply(resourceDT0);
        Assertions.assertEquals(Objects.requireNonNull(result.block()),"xxxx");
    }
}
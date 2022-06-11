package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.repositories.QuestionRepository;
import co.com.sofka.questions.usecases.questions.CreateUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CreateUseCaseTest {

    @MockBean
    private QuestionRepository questionRepository;

    @SpyBean
    private CreateUseCase createUseCase;

    @Test
    void createQuestion(){

        var questionDto = new QuestionDTO(
                "01", "XXXX", "¿Test OK?", "OPEN",
                "TECHNOLOGY");

        var question = new Question();
        question.setId("01");
        question.setUserId("xxxx");
        question.setQuestion("¿Qué es SpingBoot?");
        question.setType("SOFTWARE DEVELOPMENT");
        question.setCategory("OPEN");
        question.setUserEmail("ss.rodriguez00021@gmai");


        Mockito.when(questionRepository.save(Mockito.any(Question.class)))
                .thenReturn(Mono.just(question));

        var response = createUseCase.apply(questionDto);

        Assertions.assertEquals(response.block(), "01");

    }
}
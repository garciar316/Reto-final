package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.repositories.QuestionRepository;
import co.com.sofka.questions.usecases.questions.GetUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GetUseCaseTest {
    @MockBean
    private QuestionRepository questionRepository;
    @SpyBean
    private GetUseCase getQuestion;

    @Test
    public void get(){

        var question = new Question();
        question.setId("xxx");
        question.setUserId("yyyy");
        question.setQuestion("Que es Java?");
        question.setType("OPEN");
        question.setCategory("TECNOLOGIA");

        Mockito.when(questionRepository.findById(Mockito.any(String.class))).thenReturn(Mono.just(question));

        var respuesta = getQuestion.apply("1");
        Assertions.assertEquals(respuesta.block().getQuestion(), "Que es Java?");
    }
}
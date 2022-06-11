package co.com.sofka.questions.usecases;

import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.repositories.AnswerRepository;
import co.com.sofka.questions.repositories.QuestionRepository;
import co.com.sofka.questions.usecases.questions.DeleteUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DeleteUseCaseTest {


    @MockBean
    private AnswerRepository answerRepository;
    @MockBean
    private QuestionRepository questionRepository;

    @SpyBean
    DeleteUseCase deleteUseCase;


    @Test
    public void delete() {

        var answer = new AnswerDTO();
        answer.setQuestionId("xxx");
        answer.setUserId("yyy");
        answer.setAnswer("tecnologia");

        Mockito.when(questionRepository.deleteById("xxx")).thenReturn(Mono.empty());
        Mockito.when(answerRepository.deleteByQuestionId("xxx")).thenReturn(Mono.empty());

        var result = deleteUseCase.apply("xxx").block();

        Assertions.assertEquals(result, null);
    }
}
package co.com.sofka.questions.usecases;

import co.com.sofka.questions.mappers.QuestionMapper;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.repositories.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Service
@Validated
public class OwnerListUseCase implements Function<String, Flux<QuestionDTO>> {
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    public OwnerListUseCase(QuestionMapper questionMapper, QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
    }


    @Override
    public Flux<QuestionDTO> apply(String userId) {
        return questionRepository.findByUserId(userId)
                .map(questionMapper.questionToQuestionDTO());
    }
}

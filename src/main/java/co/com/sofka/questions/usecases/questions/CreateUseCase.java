package co.com.sofka.questions.usecases.questions;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.mappers.QuestionMapper;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.repositories.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class CreateUseCase implements SaveQuestion {
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    public CreateUseCase( QuestionRepository questionRepository, QuestionMapper questionMapper) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
    }

    @Override
    public Mono<String> apply(QuestionDTO questionDTO) {
        return questionRepository
                .save(questionMapper.questionDTOToQuestion(null).apply(questionDTO))
                .map(Question::getId);
    }

}

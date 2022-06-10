package co.com.sofka.questions.usecases.questions;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.mappers.QuestionMapper;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.repositories.QuestionRepository;
import co.com.sofka.questions.usecases.MapperUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;


@Service
@Validated
public class UpdateUseCase implements SaveQuestion {
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    public UpdateUseCase(QuestionMapper questionMapper, QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
    }

    @Override
    public Mono<String> apply(QuestionDTO dto) {
        Objects.requireNonNull(dto.getId(), "Id of the question is required");
        return questionRepository
                .save(questionMapper.questionDTOToQuestion(dto.getId()).apply(dto))
                .map(Question::getId);
    }


}

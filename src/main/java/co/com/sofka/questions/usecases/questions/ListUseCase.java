package co.com.sofka.questions.usecases.questions;

import co.com.sofka.questions.mappers.QuestionMapper;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.repositories.QuestionRepository;
import co.com.sofka.questions.usecases.MapperUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Service
@Validated
public class ListUseCase implements Supplier<Flux<QuestionDTO>> {
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    public ListUseCase(QuestionMapper questionMapper, QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
    }

    @Override
    public Flux<QuestionDTO> get() {
        return questionRepository.findAll()
                .map(questionMapper.questionToQuestionDTO());
    }

}

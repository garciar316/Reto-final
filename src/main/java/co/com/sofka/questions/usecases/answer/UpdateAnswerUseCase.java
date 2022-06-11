package co.com.sofka.questions.usecases.answer;

import co.com.sofka.questions.mappers.AnswerMapper;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.repositories.AnswerRepository;
import co.com.sofka.questions.usecases.questions.GetUseCase;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Validated
public class UpdateAnswerUseCase {

    private final AnswerRepository answerRepository;

    private final AnswerMapper answerMapper;

    private final GetUseCase getUseCase;


    public UpdateAnswerUseCase(AnswerRepository answerRepository,
                            AnswerMapper answerMapper,
                            GetUseCase getUseCase) {

        this.answerRepository = answerRepository;
        this.getUseCase = getUseCase;
        this.answerMapper = answerMapper;
    }

    public Mono<Object> apply(AnswerDTO answerDTO) {
        Objects.requireNonNull(answerDTO.getQuestionId(), "Id of the question is required");
        Objects.requireNonNull(answerDTO.getId(), "Id of the answer is required");
        return getUseCase.apply(answerDTO.getQuestionId()).flatMap(question ->
                answerRepository.save(answerMapper.answerDTOToAnswer().apply(answerDTO)));
    }
}

package co.com.sofka.questions.usecases.answer;

import co.com.sofka.questions.repositories.AnswerRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

@Service
public class DeleteAnswerUseCase implements Function<String, Mono<Void>> {

    private final AnswerRepository answerRepository;

    public DeleteAnswerUseCase(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Override
    public Mono<Void> apply(String id) {
        Objects.requireNonNull(id, "Id is required");
        return answerRepository.deleteById(id);
    }
}

package co.com.sofka.questions.repositories;

import co.com.sofka.questions.collections.Answer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public interface AnswerRepository extends ReactiveMongoRepository<Answer, String> {
    Flux<Answer> findAllByQuestionId(String id);

    Mono<Void> deleteByQuestionId(String questionId);

    Mono<Void> deleteById(String answerId);
}

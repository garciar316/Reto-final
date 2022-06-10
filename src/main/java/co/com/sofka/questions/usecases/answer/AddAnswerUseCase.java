package co.com.sofka.questions.usecases.answer;

import co.com.sofka.questions.mappers.AnswerMapper;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.repositories.AnswerRepository;
import co.com.sofka.questions.services.SendEmailService;
import co.com.sofka.questions.usecases.questions.GetUseCase;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Validated
public class AddAnswerUseCase implements SaveAnswer, SendEmailService {
    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;
    private final GetUseCase getUseCase;

    private final JavaMailSender javaMailSender;

    public AddAnswerUseCase(AnswerMapper answerMapper, GetUseCase getUseCase, AnswerRepository answerRepository, JavaMailSender javaMailSender) {
        this.answerRepository = answerRepository;
        this.getUseCase = getUseCase;
        this.answerMapper = answerMapper;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public Mono<String> sendEmail(String to, String subject, String body) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("ss.rodriguez00021@gmail.com");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);
        javaMailSender.send(simpleMailMessage);
        return Mono.just("Email Enviado");
    }

    public Mono<QuestionDTO> apply(AnswerDTO answerDTO) {
        Objects.requireNonNull(answerDTO.getQuestionId(), "Id of the answer is required");
        return getUseCase.apply(answerDTO.getQuestionId()).flatMap(question ->
                answerRepository.save(answerMapper.answerDTOToAnswer().apply(answerDTO))
                        .map(answer -> {
                            question.getAnswers().add(answerDTO);

                            var response = sendEmail(
                                    question.getUserEmail(),
                                    "Informando de respuesta a pregunta",
                                    "EL cuerpo del Email" + question.getQuestion());
                            return question;
                        })
        );
    }

}

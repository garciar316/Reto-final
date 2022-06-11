package co.com.sofka.questions;

import co.com.sofka.questions.usecases.answer.AddAnswerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import javax.mail.MessagingException;

@SpringBootApplication
public class QuestionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuestionsApplication.class, args);
    }


}

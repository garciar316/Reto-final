package co.com.sofka.questions.services;

import reactor.core.publisher.Mono;

@FunctionalInterface
public interface SendEmailService {
    Mono<String> sendEmail(String to, String subject, String body);
}

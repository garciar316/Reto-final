package co.com.sofka.questions.mappers;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.QuestionDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class QuestionMapper {

    public Function<QuestionDTO, Question> questionDTOToQuestion(String id) {
        return updateQuestion -> {
            var question = new Question();
            question.setId(id);
            question.setUserId(updateQuestion.getUserId());
            question.setCategory(updateQuestion.getCategory());
            question.setQuestion(updateQuestion.getQuestion());
            question.setType(updateQuestion.getType());
            return question;
        };
    }

    public Function<Question, QuestionDTO> questionToQuestionDTO() {
        return entity -> new QuestionDTO(
                entity.getId(),
                entity.getUserId(),
                entity.getQuestion(),
                entity.getType(),
                entity.getCategory()
        );
    }

}

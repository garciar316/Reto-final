package co.com.sofka.questions.model;


import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.Optional;

public class AnswerDTO {

    private String id;
    @NotBlank(message = "The userid must exist for this object")
    private String userId;
    @NotBlank
    private String questionId;
    @NotBlank
    private String answer;

    private Integer position;


    public AnswerDTO() {

    }

    public AnswerDTO(String questionId,String userId,
                     String answer, String id, Integer position) {
        this.id = id;
        this.userId = userId;
        this.questionId = questionId;
        this.answer = answer;
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPosition() {
        return Optional.ofNullable(position).orElse(1);
    }

    public void setPosition(Integer position) {
        this.position = position;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerDTO answerDTO = (AnswerDTO) o;
        return Objects.equals(userId, answerDTO.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return "AnswerDTO{" +
                "userId='" + userId + '\'' +
                ", questionId='" + questionId + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}

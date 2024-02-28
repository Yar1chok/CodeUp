package application.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "java_tower")
public class JavaTower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ques")
    public Long idQues;
    @Column(name = "text_ques")
    public String textQues;
    @Column(name = "right_answer")
    public String rightAnswer;

    @Column(name = "wrong_answer_1")
    public String wrongAnswer1;
    @Column(name = "wrong_answer_2")
    public String wrongAnswer2;
    @Column(name = "wrong_answer_3")
    public String wrongAnswer3;

    @Column(name = "level")
    public Integer level;

    @Column(name = "block")
    public Integer block;

    @Transient
    public List<String> shuffledAnswers;

    public List<String> getShuffledAnswers() {
        return shuffledAnswers;
    }

    public void setShuffledAnswers(List<String> shuffledAnswers) {
        this.shuffledAnswers = shuffledAnswers;
    }

    public Long getIdQues() {
        return idQues;
    }

    public void setIdQues(Long idQues) {
        this.idQues = idQues;
    }

    public String getTextQues() {
        return textQues;
    }

    public void setTextQues(String textQues) {
        this.textQues = textQues;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public String getWrongAnswer1() {
        return wrongAnswer1;
    }

    public void setWrongAnswer1(String wrongAnswer1) {
        this.wrongAnswer1 = wrongAnswer1;
    }

    public String getWrongAnswer2() {
        return wrongAnswer2;
    }

    public void setWrongAnswer2(String wrongAnswer2) {
        this.wrongAnswer2 = wrongAnswer2;
    }

    public String getWrongAnswer3() {
        return wrongAnswer3;
    }

    public void setWrongAnswer3(String wrongAnswer3) {
        this.wrongAnswer3 = wrongAnswer3;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getBlock() {
        return block;
    }

    public void setBlock(Integer block) {
        this.block = block;
    }
}

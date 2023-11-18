package application.entity;

import jakarta.persistence.*;

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
    public String level;
}

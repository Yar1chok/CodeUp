package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "level_1")
public class Level1 {
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

}

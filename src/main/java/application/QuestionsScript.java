package application;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.List;


public class QuestionsScript {
    private static List<Map> readJavaQuestions() {
        try {
            FileInputStream fileInputStream = new FileInputStream("QuesAndAnswerJava.docx");
            XWPFDocument document = new XWPFDocument(fileInputStream);
            Map<String, List<String>> questionsWithAnswers = new HashMap<>();
            Map<String, String>  questionsWithCorrectAnswer = new HashMap<>();
            Map<String, List<String>> numberWithQues = new HashMap<>();
            int skipFirstLine = 0;
            String numberQues = "";
            String question = "";
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                String text = paragraph.getText();
                if (text != null && !text.trim().isEmpty()) {
                    if (skipFirstLine < 1) {
                        skipFirstLine++;
                        continue;
                    }
                    if (text.matches("^\\d+\\.\\d+\\s.*")) {
                        numberQues = text.split(" ")[0];
                    } else if (paragraph.getRuns().get(0).isBold() && text.matches("^[a-zA-ZА-Яа-я].*")) {
                        List<String> ques;
                        if (numberWithQues.get(numberQues) == null) {
                            ques = new ArrayList<>();
                        } else {
                            ques = numberWithQues.get(numberQues);
                        }
                        ques.add(text);
                        numberWithQues.put(numberQues, ques);
                        question = text;
                        questionsWithAnswers.put(text, new ArrayList<>());
                        questionsWithCorrectAnswer.put(text, "");
                    } else if (text.matches("^[a-z]\\)\\s.*")) {
                        if (paragraph.getRuns().get(0).getCTR().newCursor().xmlText().contains("<w:highlight")) {
                            questionsWithCorrectAnswer.put(question, trimString(text));
                        } else {
                            List<String> answers = questionsWithAnswers.get(question);
                            answers.add(trimString(text));
                            questionsWithAnswers.put(question, answers);
                        }
                    }
                }
            }
            System.out.println(numberWithQues.keySet().stream().sorted().toList());
            System.out.println("Questions:");
            System.out.println(numberWithQues);

            System.out.println("Answers:");
            System.out.println(questionsWithAnswers);

            System.out.println("Correct answer:");
            System.out.println(questionsWithCorrectAnswer);

            fileInputStream.close();
            document.close();
            List<Map> result = new ArrayList<>();
            result.add(numberWithQues);
            result.add(questionsWithAnswers);
            result.add(questionsWithCorrectAnswer);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String trimString(String input) {
        if (input.length() > 3) {
            input = input.substring(3);
        } else {
            input = "";
        }
        String target = "(правильный ответ)";
        int index = input.indexOf(target);
        if (index != -1) {
            input = input.substring(0, index) + input.substring(index + target.length());
        }
        target = "(Правильный ответ)";
        index = input.indexOf(target);
        if (index != -1) {
            input = input.substring(0, index) + input.substring(index + target.length());
        }
        return input.trim();
    }

    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5222/project", "postgres", "postgres");

            String insertQuery = "INSERT INTO java_tower (text_ques, right_answer, wrong_answer_1, wrong_answer_2, wrong_answer_3, level, block) VALUES (?, ?, ?, ?, ?, ?, ?)";
            List<Map> mapList = readJavaQuestions();
            Map<String, List<String>> numberWithQues = mapList.get(0);
            Map<String, List<String>>  questionsWithAnswers = mapList.get(1);
            Map<String, String> questionsWithCorrectAnswer = mapList.get(2);
            for(String number: numberWithQues.keySet()) {
                for (String ques: numberWithQues.get(number)) {
                    List<String> wrongAnswer = questionsWithAnswers.get(ques);
                    System.out.println(number + " " + wrongAnswer + " " + wrongAnswer.size());
                    int block = Integer.parseInt(number.split("\\.")[0]);
                    int level = Integer.parseInt(number.split("\\.")[1]);;
                    preparedStatement = connection.prepareStatement(insertQuery);
                    preparedStatement.setString(1, ques);
                    preparedStatement.setString(2, questionsWithCorrectAnswer.get(ques));
                    preparedStatement.setString(3, wrongAnswer.get(0));
                    preparedStatement.setString(4, wrongAnswer.get(1));
                    preparedStatement.setString(5, wrongAnswer.get(2));
                    preparedStatement.setInt(6, level);
                    preparedStatement.setInt(7, block);

                    preparedStatement.executeUpdate();
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}


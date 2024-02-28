package application;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class QuestionsScript {
    public static void main(String[] args) {
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
                        List<String> answers = questionsWithAnswers.get(question);
                        answers.add(trimString(text));
                        questionsWithAnswers.put(question, answers);
                        if (paragraph.getRuns().get(0).getCTR().newCursor().xmlText().contains("<w:highlight")) {
                            questionsWithCorrectAnswer.put(question, trimString(text));
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
        } catch (IOException e) {
            e.printStackTrace();
        }
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

}

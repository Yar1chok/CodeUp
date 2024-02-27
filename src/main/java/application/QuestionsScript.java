package application;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionsScript {
    public static void main(String[] args) {
        try {
            FileInputStream fileInputStream = new FileInputStream("QuesAndAnswerJava.docx");
            XWPFDocument document = new XWPFDocument(fileInputStream);
            Map<String, List<String>> questionsWithAnswers = new HashMap<>();
            Map<String, String>  questionsWithCorrectAnswer = new HashMap<>();
            Map<String, String> numberWithQues = new HashMap<>();
            int skipFirstLine = 0;
            String numberQues = "";
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                String question;
                String answer;
                String text = paragraph.getText();
                if (text != null && !text.trim().isEmpty()) {
                    if (skipFirstLine < 1) {
                        skipFirstLine++;
                        continue;
                    }
                    if (text.matches("^\\d+\\.\\d+\\s.*") || text.matches("^\\d+\\.\\d+\\.\\d+\\s.*")) {
                        numberQues = text.split(" ")[0];
                    } else if (paragraph.getRuns().get(0).isBold() && text.matches("^[a-zA-ZА-Яа-я].*")) {
                        numberWithQues.put(numberQues, text);
                        questionsWithAnswers.put(text, new ArrayList<>());
                        questionsWithCorrectAnswer.put(text, "");
                    } else if (text.matches("^[a-z]\\)\\s.*")) {
                        List<String> answers = questionsWithAnswers.get(numberWithQues.get(numberQues));
                        answers.add(trimString(text));
                        questionsWithAnswers.put(numberWithQues.get(numberQues), answers);
                        if (paragraph.getRuns().get(0).getCTR().newCursor().xmlText().contains("<w:highlight")) {
                            questionsWithCorrectAnswer.put(numberWithQues.get(numberQues), trimString(text));
                        }
                    }
                }
            }
            System.out.println(numberWithQues.keySet());
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

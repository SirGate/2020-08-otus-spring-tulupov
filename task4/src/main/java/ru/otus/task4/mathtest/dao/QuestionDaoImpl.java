package ru.otus.task4.mathtest.dao;

import org.springframework.stereotype.Repository;
import ru.otus.task4.mathtest.config.LocaleProps;
import ru.otus.task4.mathtest.domain.Question;
import ru.otus.task4.mathtest.exc.QuestionsLoadException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Repository
public class QuestionDaoImpl implements QuestionDao {

    private final LocaleProps localeProps;

    private List<Question> questions = new ArrayList<>();

    public QuestionDaoImpl(LocaleProps localeProps) {
        this.localeProps = localeProps;
    }

    @Override
    public List<Question> getNewQuestions() throws QuestionsLoadException {
        String issue;
        questions = new ArrayList<>();
        try {
            InputStream i = this.getClass().getClassLoader().getResourceAsStream(localeProps.getQuestionsFile());
            BufferedReader fileInput = new BufferedReader(new InputStreamReader(i));
            while ((issue = fileInput.readLine()) != null) {
                String[] fullQuestion = issue.split(",");
                Question question = new Question(fullQuestion[0], fullQuestion[1]);
                questions.add(question);
            }
            i.close();
        } catch (Exception e) {
            throw new QuestionsLoadException(e);
        }
        return questions;
    }
}

package ru.otus.tasks.task1.service;

import org.springframework.stereotype.Service;
import ru.otus.tasks.task1.domain.Question;

import java.util.List;

@Service
public class CheckingServiceImpl implements CheckingService {
    private final IOService ioService;

    public CheckingServiceImpl(IOService ioService) {
        this.ioService = ioService;
    }

    @Override
    public int check(List<Question> questionList) {
        int result = 0;
        for (var question : questionList) {
            ioService.printlnString(question.getAsk());
            int answer = this.ioService.askInt();
            if (answer == Integer.valueOf(question.getAnswer())) {
                result++;
            }
            ioService.printMessage("answer.right");
            ioService.printlnString(question.getAnswer() + System.lineSeparator());
        }
        return result;
    }
}
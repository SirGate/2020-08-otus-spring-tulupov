package ru.otus.tasks.task1.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.tasks.task1.config.LocaleProps;
import ru.otus.tasks.task1.dao.PersonDao;
import ru.otus.tasks.task1.dao.QuestionDao;
import ru.otus.tasks.task1.domain.Person;
import ru.otus.tasks.task1.domain.Question;
import ru.otus.tasks.task1.exc.QuestionsLoadException;

import java.util.List;

@Service
public class RunnerImpl implements Runner {

    private final PersonDao personDao;
    private final QuestionDao questionDao;
    private final CheckingService checkingService;
    private final IOService consoleIOService;
    private final int sufficientResult;
    private final LocaleProps localeProps;

    public RunnerImpl(PersonDao personDao, QuestionDao questionDao,
                      CheckingService checkingService, IOService consoleIOService,
                      LocaleProps localeProps,
                      @Value("${sufficient.result}") int sufficientResult) {
        this.personDao = personDao;
        this.questionDao = questionDao;
        this.checkingService = checkingService;
        this.consoleIOService = consoleIOService;
        this.sufficientResult = sufficientResult;
        this.localeProps = localeProps;
    }

    public void startTesting() {
        chooseLanguage();
        Person person = personDao.getNewPerson();
        List<Question> questions = null;
        try {
            questions = questionDao.getNewQuestions();
        } catch (QuestionsLoadException e) {
            e.printStackTrace();
        }
        int result = checkingService.check(questions);
        printResult(person, result);
        consoleIOService.close();
    }

    private void printResult(Person person, int result) {
        consoleIOService.printMessage("answer.student");
        consoleIOService.printString(person.getFamilyName() + " " + person.getName());
        consoleIOService.printMessage("answer.result1");
        consoleIOService.printString(String.valueOf(result));
        consoleIOService.printMessage("answer.result2");
        consoleIOService.printlnString("" + System.lineSeparator());

        if (result < sufficientResult) {
            consoleIOService.printMessage("answer.notpassed");
        } else {
            consoleIOService.printMessage("answer.passed");
        }
    }

    private void chooseLanguage() {
        consoleIOService.printMessage("question.language");
        consoleIOService.printlnString("");
        Integer counter = 1;
        String[] languages = localeProps.getAvailableLanguages().keySet().
                toArray(new String[localeProps.getAvailableLanguages().size()]);
        for (String speech : languages) {
            consoleIOService.printlnString((counter++) + "." + speech);
        }
        final int language = consoleIOService.askInt();
        consoleIOService.printlnString("");
        if (language > 0 && language < languages.length + 1) {
            String locale = languages[language - 1];
            localeProps.setCurrentLocale(locale);
        } else {
            consoleIOService.printlnString("Такого языка не существует. Выбран язык по умолчанию.");
            consoleIOService.printlnString("");
        }
    }
}

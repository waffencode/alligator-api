package com.alligator.alligatorapi.service;

import com.alligator.alligatorapi.entity.sprint.AssignedTask;
import com.alligator.alligatorapi.entity.sprint.Sprint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class SprintService {


    public List<AssignedTask> suggestTaskAssignation(Sprint sprint) {

        // 1. Вытаскиваем таски из SprintTask в List<SprintTask>

        // 2. Проверка статуса задачи: назначена ли она, не завершена ли

        // 3. Сортировка по приоритетам и дедлайнам
        // (матрица Эйзенхауэра. Если задачи связанные, то рассматриваем по самой важной и срочной в цепочке)
        // Приоритеты: A, B, C, D, E.
        // Если дедлайн задачи истекает посреди спринта, Тогда выполняется в первую очередь. Hard deadline - по истечение дедлайна задача не имеет смысл
        // Мягкий дедлайн - разные варианты, например, снижается приоритет.
        // Будем ли добавлять в качестве характеристики задачи время на выполнение?

        // 4. Сопоставление по возможностям ролей
        // (у юзера может быть несколько ролей)


        Logger.getLogger(SprintService.class.getName()).info("This doesn't works yet...");
        return new ArrayList<>();
    }
}

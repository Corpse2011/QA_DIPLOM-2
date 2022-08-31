# Дипломный проект профессии «Тестировщик»

Дипломный проект представляет собой автоматизацию тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.

## Документы
* [Задание](https://github.com/Corpse2011/QA_DIPLOM-2/blob/main/docs/Zadanie.md)
* [План автоматизации](https://github.com/Corpse2011/QA_DIPLOM-2/blob/main/docs/Plan.md)
* [Отчет по итогам тестирования](https://github.com/Corpse2011/QA_DIPLOM-2/blob/main/docs/Report.md)
* [Отчет по итогам автоматизированного тестирования](https://github.com/Corpse2011/QA_DIPLOM-2/blob/main/docs/Summary.md)

Дипломный проект представляет собой автоматизацию тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.

На локальном компьютере заранее должны быть установлены IntelliJ IDEA и Docker

## Процедура запуска авто-тестов:

**1.** Склонировать на локальный репозиторий [Дипломный проект](https://github.com/netology-code/qa-diploma) и открыть его в приложении IntelliJ IDEA

**2.** Запустить Docker Desktop

**3.** Открыть проект в IntelliJ IDEA

**4.** В терминале запустить контейнеры:

    docker-compose up -d

**5.** Запустить целевое приложение:

     для mySQL: 
    java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar 

     для postgresgl:
     java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar

**5.** Открыть второй терминал

**6.** Во втором терминале запустить тесты:

    для mySQL:
    ./gradlew clean test -DurlDB="jdbc:mysql://localhost:3306/app"

    для postgresgl: 
    ./gradlew clean test -DurlDB="jdbc:postgresql://localhost:5432/app"

**7.** Создать отчёт Allure и открыть в браузере:

    ./gradlew allureServe

**8.** Для завершения работы allureServe выполнить команду:

    Ctrl+C

**9.** Для остановки работы контейнеров выполнить команду:

    docker-compose down
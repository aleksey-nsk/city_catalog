# Info:
1. Задача: загрузка данных справочника городов России
2. Подробное условие задачи в файле **files/task.pdf**
3. Файл с городами: **files/cities.csv**
4. Реализовано консольное приложение. Использован **класс Scanner**:  
`Scanner scanner = new Scanner(System.in);`  
Данный класс нужен для чтения данных из входящих потоков (это может быть консоль приложения, файл и т.д.). 
Внутри скобок передаём `System.in` так как необходимо, чтобы сканер считывал данные из консоли.  
Метод `nextLine()` обращается к источнику данных, и считывает строку:  
`String command = scanner.nextLine();`
5. Меню приложения:

                    ВЫБЕРИТЕ ТРЕБУЕМОЕ ДЕЙСТВИЕ                      
                                                                     
       [1] - Загрузить города из файла в БД                          
                                                                     
       [2] - Вывести список городов                                  
                                                                     
       [3] - Очистить таблицу с городами                             
                                                                     
       [4] - Вывести отсортированный список городов, по наименованию,
             в алфавитном порядке по убыванию, без учёта регистра    
                                                                     
       [5] - Вывести отсортированный список городов, по фед. округу, 
             и наименованию города внутри фед. округа, в алф. порядке
             по убыванию, с учётом регистра                          
                                                                     
       [6] - Поиск города с наибольшим количеством жителей           
                                                                     
       [7] - Поиск количества городов в разрезе регионов             
                                                                     
       [exit] - Выход     

6. Использована БД **Postgres** в контейнере **Docker**
7. Для выполнения запросов в БД используется класс **PreparedStatement**. Кроме собственно выполнения запроса 
этот класс позволяет **подготовить запрос**, отформатировать его должным образом

# Как развернуть Postgres в Docker:
1. Установить **Docker** и утилиту **docker-compose**:  
![](https://github.com/aleksey-nsk/city_catalog/tree/master/screenshots/00_docker_and_compose.png)  
2. Использовать **IntelliJ IDEA Ultimate**
3. В Intellij IDEA установить **плагин Docker**
4. В файле pom.xml подключить зависимость **PostgreSQL JDBC Driver**
5. В корне проекта создать файл **docker-compose.yaml**  
Содержимое файла docker-compose.yaml:  
![](https://github.com/aleksey-nsk/city_catalog/tree/master/screenshots/01_file_docker_compose.png)  
Для запуска нажать зелёную стрелку на одной строке с postgres.  
Файл должен начинаться с версии спецификации docker-compose: `version: '3'`.  
Docker-compose работает с сервисами (1 сервис = 1 контейнер). Раздел, в котором будут описаны сервисы,
начинается с `services`.  
Сервис - это контейнер. Понятное название сервиса помогает определить его роль. Здесь сервис
назван `postgres`.  
Указано проксирование портов:  
`ports:`  
`- 15432:5432`  
где `15432` - это порт хост-машины (т.е. внешний порт), `5432` - это порт внутри контейнера (т.е. внутренний порт).  
Указаны переменные окружения (используются для конфигурирования контейнера):  
`environment:`  
`- 'POSTGRES_USER=sa'`  
`- 'POSTGRES_HOST_AUTH_METHOD=trust'`  
`- 'POSTGRES_DB=java'`  
где `POSTGRES_USER=sa` - это пользователь, который будет логиниться;  
`POSTGRES_HOST_AUTH_METHOD=trust` - чтобы логиниться в базу без указания пароля;  
`POSTGRES_DB=java` - название БД, которая будет создана.
6. В нижней части IntelliJ IDEA во **вкладке Services** будет отображаться процесс:  
![](https://github.com/aleksey-nsk/city_catalog/tree/master/screenshots/02_start_docker.png)  
В итоге отобразится следующая информация:  
![](https://github.com/aleksey-nsk/city_catalog/tree/master/screenshots/03_docker_running.png)  
Это значит, что в **сервисе postgres** поднялся **контейнер java-postgres**. В папке Images должен быть **образ postgres:13**  
В консоли можно увидеть образ, контейнер и том:  
![](https://github.com/aleksey-nsk/city_catalog/tree/master/screenshots/04_image_container_volume.png)   
7. В dao-слое должны использоваться следующие значения:

       DB_URL = "jdbc:postgresql://localhost:15432/postgres";
       DB_USERNAME = "sa";
       DB_PASSWORD = "";

8. Далее настроить подключение к БД на вкладке Database:  
![](https://github.com/aleksey-nsk/city_catalog/tree/master/screenshots/05_data_source.png)  
9. Готово. Далее можно запустить приложение
10. [Инструкция](https://javarush.ru/groups/posts/3688-kak-razvernutjh-postgres-v-docker-i-podkljuchitjh-ee-k-spring-boot-prilozheniju) по разворачиванию Postgres в Docker

# Ещё инфа про Docker:
- [Мануал по основам Docker](https://habr.com/ru/company/ruvds/blog/438796/)
- [Основные команды Docker](https://timeweb.com/ru/community/articles/osnovnye-komandy-docker)
- [Видео по работе с Docker в IntelliJ IDEA](https://www.youtube.com/watch?v=ck6xQqSOlpw)

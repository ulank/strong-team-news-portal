# Backend Test Assignment

[![Linkedin](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://linkedin.com/in/ulan-kozhabekov-7b7991217/)
[![Instagram](https://img.shields.io/badge/Instagram-E4405F?style=for-the-badge&logo=instagram&logoColor=white)](https://www.instagram.com/ulaburrito/)
[![Telegram](https://img.shields.io/badge/Telegram-2CA5E0?style=for-the-badge&logo=telegram&logoColor=white)](https://t.me/nadevvo)

<img src="https://strongte.am/logo.e9e07396.svg" alt="strong-team-icon"/>

Выполнил задание Улан Кожабеков

ulankdt@gmail.com | +77087314002

---

# Применение

```shell
docker compose -f docker-compose-local.yml up -d
```

NOTE: Проверьте доступен ли внейшний порт <code> 5433 </code>

```yaml
version: '3.9'

services:
  news-portal-db:
    container_name: news-portal-db
    image: postgres:15
    command:
      - "postgres"
      - "-c"
      - "max_connections=50"
      - "-c"
      - "shared_buffers=1GB"
      - "-c"
      - "effective_cache_size=4GB"
      - "-c"
      - "work_mem=16MB"
      - "-c"
      - "maintenance_work_mem=512MB"
      - "-c"
      - "random_page_cost=1.1"
      - "-c"
      - "temp_file_limit=10GB"
      - "-c"
      - "log_min_duration_statement=200ms"
      - "-c"
      - "idle_in_transaction_session_timeout=10s"
      - "-c"
      - "lock_timeout=1s"
      - "-c"
      - "statement_timeout=60s"
      - "-c"
      - "shared_preload_libraries=pg_stat_statements"
      - "-c"
      - "pg_stat_statements.max=10000"
      - "-c"
      - "pg_stat_statements.track=all"
    ports:
      - "5433:5432"
    environment:
      POSTGRES_DB: news-portal-db
      POSTGRES_USER: news-portal
      POSTGRES_PASSWORD: pWiUuHyyb3Wl
    healthcheck:
      test: [ "CMD-SHELL", "pg_isready -U news-portal -d news-portal" ]
      interval: 10s
      timeout: 5s
      retries: 5
      start_period: 10s
    restart: unless-stopped
```

Дальше запустите проект и запуститься миграция всех таблиц с помощью **Liquidbase**.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<databaseChangeLog
        xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
	  http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.8.xsd">

    <changeSet id="1" author="Ulan" failOnError="true">
        <sqlFile path="db/changes/230512_1_create_auth_tables.sql"/>
    </changeSet>

    <changeSet id="2" author="Ulan" failOnError="true">
        <sqlFile path="db/changes/230512_2_insert_roles.sql"/>
    </changeSet>

    <changeSet id="3" author="Ulan" failOnError="true">
        <sqlFile path="db/changes/230512_3_create_logic_tables.sql"/>
    </changeSet>

    <changeSet id="4" author="Ulan" failOnError="true">
        <sqlFile path="db/changes/230512_4_alter_news_table.sql"/>
    </changeSet>

    <changeSet id="5" author="Ulan" failOnError="true">
        <sqlFile path="db/changes/230512_5_alter_news_table.sql"/>
    </changeSet>

    <changeSet id="6" author="Ulan" failOnError="true">
        <sqlFile path="db/changes/230512_6_alter_news_table.sql"/>
    </changeSet>

</databaseChangeLog>
```

Liquibase - это инструмент для управления миграциями баз данных. Он позволяет разработчикам и администраторам баз данных
управлять изменениями в базах данных в контролируемой и систематической манере. С помощью Liquibase можно создавать,
изменять и откатывать изменения в базах данных.

После запуска скриптов финальный результат будет такие:

<img src="assets/database_after_migration.png" alt="database"/>

Если у вас не видна схема <code>private</code>, то нажмите на <code>1 of 4</code> и там в схема нажмите галочку.

- Схема private - для работы с бизнес логики
- Схема public - для changelog-ов

## Документация для запросов

Swagger OpenAPI - это спецификация, которая позволяет описывать RESTful API в машиночитаемой форме. Swagger позволяет
разработчикам и пользователям легко понимать, как работает API, какие запросы можно отправлять и какие данные получать в
ответ.

Ссылка для OpenAPI:

```
http://localhost:8089/swagger-ui/index.html#/
```

Для того, чтобы получить доступ к защищенным запросам (POST, PUT, PATCH, DELETE), необходимо получить токен.

Ниже приведена таблица с котроллерами и их описанием:

| Контроллеры           |                          Описание                           |
|:----------------------|:-----------------------------------------------------------:|
| **Auth Controller**   |          Контроллер _для авторизации пользователя_          | 
| **News Controller**   |                  Контроллер _для новостей_                  | 
| **Source Controller** |            Контроллер _для источников новостей_             | 
| **Topic Controller**  |                Контроллер _для тем новостей_                | 
| **Demo Controller**   | Контроллер _для проверки работоспособности Spring Security_ | 

---
<img src="assets/swagger-lock.png"  alt="swagger-lock"/>
---
После перейдите на [Auth Controller APIs](http://localhost:8089/swagger-ui/index.html#/Auth)

и зарегайтесь <code>username</code> и <code>email</code> должен быть уникальным.

Таков ответ дольжен вернуть.

```json
{
  "access_token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1bGFudGFpIiwiaWF0IjoxNjgzOTc2NTg1LCJleHAiOjE2ODQwNjI5ODV9.FIz0DOcvn2FB52xFrclMG8Isb6HZ4I9w2BmjCxfnBUo",
  "refresh_token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1bGFudGFpIiwiaWF0IjoxNjgzOTc2NTg1LCJleHAiOjE2ODQ1ODEzODV9.htUgEchcPxtiePsKd14V7NWqnt0kFSvtHMSlcj_bR0I"
}
```

Дальше присвоиваете токен:

<img src="assets/unlock.png"  alt="unlock"/>

Если токен не присвоен или просрочился то выйдет

```
 401 Unauthorized
```

---

| REST запросы                                                        |                   Ссылки                    |
|:--------------------------------------------------------------------|:-------------------------------------------:|
| GET, POST, PUT, DELETE методы для источников новостей;              |           Source Controller APIs            | 
| GET, POST, PUT, DELETE методы для новостей;                         |            News Controller APIs             | 
| GET, POST, PUT, DELETE методы для новостных тем;                    |            Topic Controller APIs            | 
| GET метод получения списка всех источников новостей;                |               /api/v1/source                | 
| GET метод получения списка всех тем новостей;                       |                /api/v1/topic                | 
| GET метод получения списка всех новостей (с пагинацией);            |           /api/v1/news/pagination           | 
| GET метод получения списка новостей по id источника (с пагинацией); | /api/v1/news/pagination/sourceId/{sourceId} | 
| GET метод получения списка новостей по id темы (с пагинацией);      |  /api/v1/news/pagination/topicId/{topicId}  | 

---

## Документация планнеров
  
```java
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Almaty")
    public void storingNewsEachSources() {
        log.info(MASK_LOG + "Stored sources to temporary directory starting" + MASK_LOG);

        List<Source> sources = sourceRepository.findAll();

        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
        gsonBuilder.registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeTypeAdapter());

        Gson gson = gsonBuilder
                .create();

        if (sources.size() > 0)
            sources.forEach(source -> {
                File folder = new File(TEMP_FOLDER_LOCATION + "/" + source.getName());
                if (!folder.exists()) {
                    if (folder.mkdir()) {
                        File file = new File(TEMP_FOLDER_LOCATION + "/" + source.getName() + "/" + LocalDateTime.now().format(LOCAL_TIME_FORMATTER) + "-" + (source.getId() != null ? source.getId() : source.getName()) + FILE_TYPE);
                        try {
                            if (file.createNewFile()) {
                                FileWriter fileWriter = new FileWriter(file);

                                List<News> news;

                                if (source.getId() != null) {
                                    news = newsRepository.findNewsBySourceId(source.getId());
                                } else {
                                    news = newsRepository.findNewsBySourceName(source.getName());
                                }

                                fileWriter.write(gson.toJson(news));
                                fileWriter.close();
                            } else {
                                System.out.println("Failed to create file!");
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    } else {
                        System.out.println("Failed to create directory!");
                    }
                }
            });

        log.info(MASK_LOG + "Stored sources to temporary directory is done" + MASK_LOG);
    }
```

Все источники храняться в <code>src/main/resources/temp</code> сначал открывается папка для источника затем заполняется новостями.

<img src="assets/storing.png"  alt="unlock"/>























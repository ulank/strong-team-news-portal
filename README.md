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

NOTE: Проверьте доступен ли внешний порт <code> 5433 </code>

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

Дальше запустите проект и запуститься миграция всех таблиц с помощью **Liquibase**.

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

После запуска скриптов финальный результат будет таким:

<img src="assets/database_after_migration.png" alt="database"/>

Если у вас не видна схема <code>private</code>, то нажмите на <code>1 of 4</code> и там в схема нажмите галочку.

- Схема private - для работы бизнес логики
- Схема public - для changelog-ов

## Документация для запросов

Swagger OpenAPI - это спецификация, которая позволяет описывать RESTful API в машиночитаемой форме. Swagger позволяет
разработчикам и пользователям легко понимать, как работает API, какие запросы можно отправлять и какие данные получать в
ответ.

Ссылка для OpenAPI:

```
http://localhost:8089/swagger-ui/index.html#/
```

Для того чтобы получить доступ к защищенным запросам (POST, PUT, PATCH, DELETE), необходимо получить токен.

Ниже приведена таблица с контроллерами и их описанием:

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

и зарегистрироваться <code>username</code> и <code>email</code> должен быть уникальным.

Таков ответ должен вернуть.

```json
{
  "access_token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1bGFudGFpIiwiaWF0IjoxNjgzOTc2NTg1LCJleHAiOjE2ODQwNjI5ODV9.FIz0DOcvn2FB52xFrclMG8Isb6HZ4I9w2BmjCxfnBUo",
  "refresh_token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1bGFudGFpIiwiaWF0IjoxNjgzOTc2NTg1LCJleHAiOjE2ODQ1ODEzODV9.htUgEchcPxtiePsKd14V7NWqnt0kFSvtHMSlcj_bR0I"
}
```

Дальше присваиваете токен:

<img src="assets/unlock.png"  alt="unlock"/>

Если токен не присвоен или просрочен, то выйдет:

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

## Примеры для POST /api/v1/news

Первый пример:

```json
{
  "title": "New Study Shows Benefits of Exercise for Mental Health",
  "content": "A new study published in the Journal of Mental Health has shown that regular exercise can have a significant positive impact on mental health. The study followed a group of participants over the course of six months, and found that those who exercised regularly reported lower levels of depression and anxiety, and higher levels of overall well-being.",
  "description": "A new study has shown that regular exercise can have a significant positive impact on mental health.",
  "author": "John Smith",
  "url": "https://example.com/new-study-shows-benefits-of-exercise",
  "urlToImage": "https://example.com/images/new-study.jpg",
  "topics": [
    {
      "name": "Mental Health",
      "description": "News and information related to mental health"
    },
    {
      "name": "Exercise",
      "description": "News and information related to exercise and physical fitness"
    }
  ],
  "source": {
    "id": "example",
    "name": "Example News",
    "url": "https://example.com/"
  }
}
```

Второй пример:

```json
{
  "title": "New Study Finds Link Between Social Media Use and Decreased Self-Esteem",
  "content": "A new study published in the Journal of Social Psychology has found a link between social media use and decreased self-esteem. The study surveyed a group of participants over the course of six months, and found that those who spent more time on social media reported lower levels of self-esteem than those who spent less time on social media.",
  "description": "A new study has found a link between social media use and decreased self-esteem.",
  "author": "Mark Wilson",
  "url": "https://example.com/new-study-finds-link-between-social-media-and-self-esteem",
  "urlToImage": "https://example.com/images/social-media-study.jpg",
  "topics": [
    {
      "name": "Social Media",
      "description": "News and information related to social media and online communication"
    },
    {
      "name": "Mental Health",
      "description": "News and information related to mental health"
    }
  ],
  "source": {
    "id": "example",
    "name": "Example News",
    "url": "https://example.com/"
  }
}
```

Третий пример:

```json
{
  "title": "New Study Finds Link Between Coffee Consumption and Reduced Risk of Alzheimer's Disease",
  "content": "A new study published in the Journal of Alzheimer's Disease has found a link between coffee consumption and a reduced risk of developing Alzheimer's disease. The study followed a group of participants over the course of ten years, and found that those who drank three to five cups of coffee per day had a 30% lower risk of developing Alzheimer's disease than those who drank less than two cups per day.",
  "description": "A new study has found a link between coffee consumption and a reduced risk of developing Alzheimer's disease.",
  "author": "Jane Doe",
  "url": "https://example.com/new-study-finds-link-between-coffee-and-alzheimers",
  "urlToImage": "https://example.com/images/coffee-study.jpg",
  "topics": [
    {
      "name": "Alzheimer's Disease",
      "description": "News and information related to Alzheimer's disease and dementia"
    },
    {
      "name": "Coffee",
      "description": "News and information related to coffee and caffeine"
    }
  ],
  "source": {
    "id": "example",
    "name": "Example News",
    "url": "https://example.com/"
  }
}
```

---

## Документация планеров

```java
@Scheduled(cron = "0 0 0 * * *", zone = "Asia/Almaty")
public void storingNewsEachSources(){
        log.info(MASK_LOG+"Stored sources to temporary directory starting"+MASK_LOG);

        List<Source> sources=sourceRepository.findAll();

        GsonBuilder gsonBuilder=new GsonBuilder();

        gsonBuilder.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
        gsonBuilder.registerTypeAdapter(ZonedDateTime.class,new ZonedDateTimeTypeAdapter());

        Gson gson=gsonBuilder
        .create();

        if(sources.size()>0)
        sources.forEach(source->{
        File folder=new File(TEMP_FOLDER_LOCATION+"/"+source.getName());
        if(!folder.exists()){
        if(folder.mkdir()){
        File file=new File(TEMP_FOLDER_LOCATION+"/"+source.getName()+"/"+LocalDateTime.now().format(LOCAL_TIME_FORMATTER)+"-"+(source.getId()!=null?source.getId():source.getName())+FILE_TYPE);
        try{
        if(file.createNewFile()){
        FileWriter fileWriter=new FileWriter(file);

        List<News> news;

        if(source.getId()!=null){
        news=newsRepository.findNewsBySourceId(source.getId());
        }else{
        news=newsRepository.findNewsBySourceName(source.getName());
        }

        fileWriter.write(gson.toJson(news));
        fileWriter.close();
        }else{
        System.out.println("Failed to create file!");
        }
        }catch(IOException e){
        throw new RuntimeException(e);
        }

        }else{
        System.out.println("Failed to create directory!");
        }
        }
        });

        log.info(MASK_LOG+"Stored sources to temporary directory is done"+MASK_LOG);
        }
```

Все источники хранятся в <code>src/main/resources/temp</code> сначала открывается папка для источника затем заполняется
новостями.

<img src="assets/storing.png"  alt="unlock"/>

Я добавил в <code>.gitignore</code> вдруг случайно запушу 😂

---

## Интеграция NewsAPI

Также я написал интеграцию с [NewsAPI](https://newsapi.org/).

Там используют <code>apiKey</code> я поставил свой ключ, если вдруг ключ перестанет работать то зарегистрируйтесь и вставьте
ключ <code>apiKey</code> в <code>application.yml</code>

```yaml
integration:
  newsapi:
    # noinspection SpringBootApplicationYaml
    url: https://newsapi.org/v2
    # noinspection SpringBootApplicationYaml
    apiKey: c331c76120524c5eba47396c4b1d27db
```

Реализовал интеграцию с помощью OpenFeign.

```xml

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
    <version>${spring.cloud.openfeign.version}</version>
</dependency>

```

Также добавил планер чтобы каждую ночь в 23:00 сохраняет новости из случайной страны:

```java
  @Scheduled(cron = "0 0 23 * * *", zone = "Asia/Almaty")
public void fetchingAndSaveNewsFromRandomCountry(){
        log.info(MASK_LOG+"Fetching.."+MASK_LOG);

        String randomCountry=countries.stream().skip(new Random().nextInt(countries.size())).findFirst().orElse("us");

        log.info(MASK_LOG+"Random country - "+randomCountry+MASK_LOG);

        List<News> news=newsService.saveNewsByNewsApi(randomCountry);

        log.info(MASK_LOG+"News size : "+news.size()+MASK_LOG);

        log.info(MASK_LOG+"Fetching is done and saved to database"+MASK_LOG);
        }
```

По запросам тоже добавил что по ключевому слову могли находить новости и сохранять у себя в базе:

<img src="assets/slug.png"  alt="unlock"/>

---

## Обратная связь

Если у вас есть какие-либо вопросы, пожалуйста, свяжитесь со мной по адресу ulankdt@gmail.com.















package kz.ulank.strongteamnewsportal.scheduler;

import com.google.gson.*;
import kz.ulank.strongteamnewsportal.entity.News;
import kz.ulank.strongteamnewsportal.entity.Source;
import kz.ulank.strongteamnewsportal.repository.NewsRepository;
import kz.ulank.strongteamnewsportal.repository.SourceRepository;
import kz.ulank.strongteamnewsportal.util.adapter.HibernateProxyTypeAdapter;
import kz.ulank.strongteamnewsportal.util.adapter.ZonedDateTimeTypeAdapter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by Ulan on 5/13/2023
 */
@Component
@RequiredArgsConstructor
public class SchedulerForStoringNews {
    private static final Logger log = LoggerFactory.getLogger(SchedulerForStoringNews.class);
    private final DateTimeFormatter LOCAL_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
    private final String TEMP_FOLDER_LOCATION = "src/main/resources/temp";
    private final String FILE_TYPE = ".json";

    private final String MASK_LOG = " **[STORING-SCHEDULER]** ";
    private final SourceRepository sourceRepository;
    private final NewsRepository newsRepository;

    /**
     * This method is scheduled to run every day at midnight (in the Asia/Almaty time zone). It is responsible for storing the news sources in files.
     * Two scheduler one for testing, another was on task
     */
    @Scheduled(cron = "0 * * * * *")
//    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Almaty")
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
}

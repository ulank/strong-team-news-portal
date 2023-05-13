package kz.ulank.strongteamnewsportal.scheduler;

import kz.ulank.strongteamnewsportal.entity.News;
import kz.ulank.strongteamnewsportal.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * Created by Ulan on 5/13/2023
 */
@Component
@RequiredArgsConstructor
public class SchedulerForFetchingApi {
    private static final Logger log = LoggerFactory.getLogger(SchedulerForFetchingApi.class);

    public final Set<String> countries = new HashSet<>(List.of("ae", "ar", "at", "au", "be", "bg", "br", "ca", "ch", "cn", "co", "cu", "cz", "de", "eg", "fr", "gb", "gr", "hk", "hu", "id", "ie", "il", "in", "it", "jp", "kr", "lt", "lv", "ma", "mx", "my", "ng", "nl", "no", "nz", "ph", "pl", "pt", "ro", "rs", "ru", "sa", "se", "sg", "si", "sk", "th", "tr", "tw", "ua", "us", "ve"));

    private final NewsService newsService;

    private final String MASK_LOG = " **[FETCHING-SCHEDULER]** ";

    /**
     * This method is scheduled to run every day at midnight (in the Asia/Almaty time zone). It is responsible for storing the news sources in files.
     * Two scheduler one for testing, another was on task
     */
//    @Scheduled(cron = "0 * * * * *")
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Almaty")
    public void fetchingAndSaveNewsFromRandomCountry() {
        log.info(MASK_LOG + "Fetching.." + MASK_LOG);

        String randomCountry = countries.stream().skip(new Random().nextInt(countries.size())).findFirst().orElse("us");

        log.info(MASK_LOG + "Random country - " + randomCountry + MASK_LOG);

        List<News> news = newsService.saveNewsByNewsApi(randomCountry);

        log.info(MASK_LOG + "News size : " + news.size() + MASK_LOG);

        log.info(MASK_LOG + "Fetching is done and saved to database" + MASK_LOG);
    }


}

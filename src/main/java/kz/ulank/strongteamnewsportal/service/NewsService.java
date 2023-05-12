package kz.ulank.strongteamnewsportal.service;

import kz.ulank.strongteamnewsportal.entity.News;
import kz.ulank.strongteamnewsportal.model.dto.NewsDto;
import kz.ulank.strongteamnewsportal.util.CrudOperation;

import java.util.UUID;

/**
 * Created by Ulan on 5/12/2023
 */
public interface NewsService extends CrudOperation<News, NewsDto, UUID> {
}

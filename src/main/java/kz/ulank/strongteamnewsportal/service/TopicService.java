package kz.ulank.strongteamnewsportal.service;

import kz.ulank.strongteamnewsportal.entity.Topic;
import kz.ulank.strongteamnewsportal.model.dto.TopicDto;
import kz.ulank.strongteamnewsportal.util.operation.CrudOperation;


/**
 * Created by Ulan on 5/12/2023
 */
public interface TopicService extends CrudOperation<Topic, TopicDto, Long> {
}

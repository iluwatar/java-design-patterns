package com.iluwatar.saga.orchestration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.iluwatar.saga.orchestration.Utility.sleepInSec;

public abstract class Service<K> implements Chapter<K> {
    private static final Logger logger = LoggerFactory.getLogger(Service.class);

    @Override
    public abstract String getName();


    @Override
    public ChapterResult<K> process(K value) {
        logger.info("The chapter about {} is starting", getName());
        logger.info("storing or calculating a data {} to db", value);
        sleepInSec(1);
        logger.info("the data {} has been stored or calculated successfully", value);
        return ChapterResult.success(value);
    }

    @Override
    public ChapterResult<K> rollback(K value) {
        logger.info("Something is going wrong hence The chapter about {} is starting the rollback procedure", getName());
        sleepInSec(1);
        logger.info("the data {} has been rollbacked successfully", value);
        return ChapterResult.success(value);
    }


}

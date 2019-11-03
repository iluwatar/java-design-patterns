package com.iluwatar.saga.choreography;

import com.iluwatar.saga.ServiceDiscoveryService;
import com.iluwatar.saga.orchestration.Chapter;
import com.iluwatar.saga.orchestration.ChapterResult;
import com.iluwatar.saga.orchestration.OrchestrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ChoreographyService<K>  implements Chapter<K> {

    protected static final Logger logger = LoggerFactory.getLogger(OrchestrationService.class);

    private final ServiceDiscoveryService service;
    protected ChoreographyService(ServiceDiscoveryService service) {
        this.service = service;
    }

    @Override
    public ChapterResult<K> process(K value) {
        logger.info("The chapter '{}' has been started. The data {} has been stored or calculated successfully",
                getName(),value);
        return ChapterResult.success(value);
    }

    @Override
    public ChapterResult<K> rollback(K value) {
        logger.info("The Rollback for a chapter '{}' has been started. The data {} has been rollbacked successfully",
                getName(),value);
        return ChapterResult.success(value);
    }

}

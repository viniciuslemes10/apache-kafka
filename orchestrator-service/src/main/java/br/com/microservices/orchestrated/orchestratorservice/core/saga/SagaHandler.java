package br.com.microservices.orchestrated.orchestratorservice.core.saga;

import static br.com.microservices.orchestrated.orchestratorservice.core.enuns.EEventSource.*;
import static br.com.microservices.orchestrated.orchestratorservice.core.enuns.ESagaStatus.FAIL;
import static br.com.microservices.orchestrated.orchestratorservice.core.enuns.ESagaStatus.SUCCESS;
import static br.com.microservices.orchestrated.orchestratorservice.core.enuns.ETopics.*;

public final class SagaHandler {

    public SagaHandler() {

    }

    public static final Object[][] SAGA_HANDLER = {
            { ORCHESTRATOR, SUCCESS, PRODUCT_VALIDATION_SUCCESS },
            { ORCHESTRATOR, FAIL, FINISH_FAIL },

            { PRODUCT_VALIDATION_SERVICE, FAIL, PRODUCT_VALIDATION_FAIL },
            { PRODUCT_VALIDATION_SERVICE, FAIL, FINISH_FAIL },
            { PRODUCT_VALIDATION_SERVICE, SUCCESS, PAYMENT_SUCCESS },

            { PAYMENT_SERVICE, FAIL, PAYMENT_FAIL },
            { PAYMENT_SERVICE, FAIL, PRODUCT_VALIDATION_FAIL },
            { PAYMENT_SERVICE, SUCCESS, INVENTORY_SUCCESS },

            { INVENTORY_SERVICE, FAIL, INVENTORY_FAIL },
            { INVENTORY_SERVICE, FAIL, PAYMENT_FAIL },
            { INVENTORY_SERVICE, SUCCESS, FINISH_SUCCESS }
    };

    public static final int EVENT_SOURCE_INDEX = 0;
    public static final int SAGA_STATUS_INDEX = 1;
    public static final int TOPIC_INDEX = 2;

}

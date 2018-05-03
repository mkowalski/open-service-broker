package com.swisscom.cloud.sb.broker.metrics

import com.swisscom.cloud.sb.broker.model.ServiceInstance
import groovy.transform.CompileStatic
import org.springframework.boot.actuate.metrics.Metric
import org.springframework.stereotype.Service

@Service
@CompileStatic
class ProvisioningMetricsService extends ServiceBrokerMetrics {

    private final String PROVISIONED_INSTANCES = "provisionedInstances"

    @Override
    boolean considerServiceInstance(ServiceInstance serviceInstance) {
        // service instance should only be considered if it hasn't been deleted, yet
        return !serviceInstance.deleted
    }

    @Override
    String tag() {
        return ProvisioningMetricsService.class.getSimpleName()
    }

    @Override
    Collection<Metric<?>> metrics() {
        List<Metric<?>> metrics = new ArrayList<>()

        retrieveTotalMetrics()
        metrics.add(new Metric<Long>("${PROVISIONED_INSTANCES}.${TOTAL}.${TOTAL}", total))
        metrics.add(new Metric<Long>("${PROVISIONED_INSTANCES}.${TOTAL}.${SUCCESS}", totalSuccess))
        metrics.add(new Metric<Long>("${PROVISIONED_INSTANCES}.${TOTAL}.${FAIL}", totalFailure))
        metrics.add(new Metric<Double>("${PROVISIONED_INSTANCES}.${SUCCESS}.${RATIO}", calculateRatio(total, totalSuccess)))
        metrics.add(new Metric<Double>("${PROVISIONED_INSTANCES}.${FAIL}.${RATIO}", calculateRatio(total, totalFailure)))

        retrieveTotalMetricsPerService()
        metrics = addCountersFromHashMapToMetrics(totalPerService, totalPerService, metrics, PROVISIONED_INSTANCES, SERVICE, TOTAL)
        metrics = addCountersFromHashMapToMetrics(totalPerService, totalSuccessPerService, metrics, PROVISIONED_INSTANCES, SERVICE, SUCCESS)
        metrics = addCountersFromHashMapToMetrics(totalPerService, totalFailurePerService, metrics, PROVISIONED_INSTANCES, SERVICE, FAIL)

        retrieveTotalMetricsPerPlan()
        metrics = addCountersFromHashMapToMetrics(totalPerPlan, totalPerPlan, metrics, PROVISIONED_INSTANCES, PLAN, TOTAL)
        metrics = addCountersFromHashMapToMetrics(totalPerPlan, totalSuccessPerPlan, metrics, PROVISIONED_INSTANCES, PLAN, SUCCESS)
        metrics = addCountersFromHashMapToMetrics(totalPerPlan, totalFailurePerPlan, metrics, PROVISIONED_INSTANCES, PLAN, FAIL)

        /*def influx = new InfluxDBConnector().influxMetricsWriter()
        for(Metric<?> m: metrics) {
            influx.set(m)
        }*/

        return metrics
    }
}
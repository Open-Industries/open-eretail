package org.openindustries.eretail.functions.storesla;

import io.quarkus.funqy.Funq;

import java.util.ArrayList;
import java.util.List;

/**
 * Serverless function (Knative / OpenShift Serverless).
 * Compares queue times and on-shelf availability against store targets; one finding per breach.
 * Compares measured values with targets and returns a verdict with one finding per
 * breach — machine-checkable assurance evidence.
 */
public class Function {

    public record Kpi(String storeId, int measuredQueueMinutes, int targetQueueMinutes,
                      double measuredOnShelfAvailabilityPct, double targetOnShelfAvailabilityPct) {
    }

    public record Verdict(String storeId, boolean compliant, List<String> findings) {
    }

    @Funq
    public Verdict storeSlaEvaluator(Kpi kpi) {
        List<String> findings = new ArrayList<>();
        if (kpi.measuredQueueMinutes() > kpi.targetQueueMinutes()) {
            findings.add("queueMinutes " + kpi.measuredQueueMinutes() + " exceeds target " + kpi.targetQueueMinutes());
        }
        if (kpi.measuredOnShelfAvailabilityPct() < kpi.targetOnShelfAvailabilityPct()) {
            findings.add("onShelfAvailabilityPct " + kpi.measuredOnShelfAvailabilityPct() + " below target " + kpi.targetOnShelfAvailabilityPct());
        }
        return new Verdict(kpi.storeId(), findings.isEmpty(), findings);
    }
}

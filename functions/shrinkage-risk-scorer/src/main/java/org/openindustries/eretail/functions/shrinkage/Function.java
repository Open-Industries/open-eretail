package org.openindustries.eretail.functions.shrinkage;

import io.quarkus.funqy.Funq;

/**
 * Serverless function (Knative / OpenShift Serverless).
 * Scores store shrinkage signals (shrinkage, discrepancies, incidents) with an auditable rationale.
 * Transparent, auditable rules: NORMAL (< 35), WARNING (35-69), CRITICAL (>= 70);
 * the rationale is returned with the score for audit (ISO/IEC 27002 A.8.15).
 * Stateless by design (12-Factor VI): all state arrives in the request.
 */
public class Function {

    public record Event(String storeId, int shrinkageIndex, int discrepancyIndex, int incidentIndex) {
    }

    public record Score(String storeId, int score, String decision, String rationale) {
    }

    @Funq
    public Score shrinkageRiskScorer(Event event) {
        int score = 0;
        StringBuilder why = new StringBuilder();
        if (event.shrinkageIndex() >= 70) {
            score += 40;
            why.append("shrinkageIndex >= 70; ");
        }
        if (event.discrepancyIndex() >= 60) {
            score += 30;
            why.append("discrepancyIndex >= 60; ");
        }
        if (event.incidentIndex() >= 50) {
            score += 30;
            why.append("incidentIndex >= 50; ");
        }
        String decision = score >= 70 ? "CRITICAL" : score >= 35 ? "WARNING" : "NORMAL";
        return new Score(event.storeId(), score, decision,
                why.length() == 0 ? "no signals" : why.toString().trim());
    }
}

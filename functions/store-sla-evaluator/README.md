# Store SLA Evaluator (serverless function)

Quarkus Funqy function on Knative (scale to zero). Compares queue times and on-shelf availability against store targets; one finding per breach.

Invoke: `POST /storeSlaEvaluator` with the KPI payload.

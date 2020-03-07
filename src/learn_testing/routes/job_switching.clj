(ns learn-testing.routes.job-switching
  (:require
   [compojure.api.sweet :refer [context GET]]
   [ring.util.http-response :refer [ok]]
   [learn-testing.api.job-switching :refer [probablity-of-switching]]))

(def job-switching-routes
  (context "/api/v1/job-switching" []
           (GET "/" {:as request}
                :description "tbd"
                (probablity-of-switching (get request :query-params)))))

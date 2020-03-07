(ns learn-testing.server
  (:gen-class)
  (:require
   [compojure.api.sweet :refer :all]
   [org.httpkit.server :refer [run-server]]
   [ring.adapter.jetty :refer [run-jetty]]
   [config.core :refer [env]]
   [ring.middleware.reload :refer [wrap-reload]]
   [learn-testing.routes.job-switching :refer [job-switching-routes]]))


(def app
  (api
   job-switching-routes))


(defn -main [& args]
  (run-jetty (wrap-reload #'app) {:port 8080
                                  :send-server-version? false
                                   }))

(ns learn-testing.api.missile-launch-test
  (:require
   [clojure.test :refer :all]
   [learn-testing.core :refer :all]
   [learn-testing.fixtures :as fix]
   [ring.mock.request :as mock]
   [cheshire.core :as cheshire]
   [learn-testing.server :refer [app]]
   [clj-http.client :as http])
  (:use clj-http.fake))


(defn parse-body [body]
  (cheshire/parse-string (slurp body) true))


(deftest launnch-missile
  (is (= (with-fake-routes
           {#"http://drdo.com:2020/launch-missile.*"
            (fn [request]
              {:status 200 :headers {} :body "missile launched. Happy world war3!"})}
           (:body (http/get "http://drdo.com:2020/launch-missile-alpha")))
         "missile launched. Happy world war3!")))

(run-tests)


(ns learn-testing.api.job-switching-test
  (:require [clojure.test :refer :all]
            [learn-testing.core :refer :all]
            [learn-testing.api.job-switching :as job-switching]
            [learn-testing.fixtures :as fix]
            [ring.mock.request :as mock]
            [cheshire.core :as cheshire]
            [learn-testing.server :refer [app]])
  (:use [clj-http.fake]
        [clojure.test]
        :reload-all))

;(use-fixtures :once fix/integration-init)

(defn parse-body [body]
  (cheshire/parse-string (slurp body) true))

(deftest job-switching-test
  (testing "Test Job switching endpoint"
    (let [response (app (-> (mock/request :get "/api/v1/job-switching?total-years=2")))
          body (parse-body (:body response))]
      (is (= (:status response 200)))
      (is (= true (:success body))))))

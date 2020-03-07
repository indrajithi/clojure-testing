(ns learn-testing.core-test
  (:require [clojure.test :refer :all]
            [learn-testing.core :refer :all]
            [learn-testing.fixtures :as fix]))

(use-fixtures :once fix/integration-init)

(deftest addmeup-test
  (testing "testing addmeup"
    (is (= (addmeup 1 2 3 4) 10))))





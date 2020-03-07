(ns learn-testing.core-test
  (:require [clojure.test :refer :all]
            [learn-testing.core :refer :all]))

(deftest addmeup-test
  (testing "testing addmeup"
    (is (= (addmeup 1 2 3 4) 10))))

(run-tests)

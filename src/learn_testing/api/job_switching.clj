(ns learn-testing.api.job-switching
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response  :as respond]))


(defn probablity-density [x mean std]
  (let [var (Math/pow std 2)
        denom (Math/sqrt (* 2 Math/PI  var))
        numer (Math/exp (/ (- (Math/pow (- x mean) 2)) (* 2 var)))]
    (/ numer denom)))

(defn probablity-of-switching
  "Calculate probablity of switching given the tenure in the current company"
  [kwargs]
  (let [query (clojure.walk/keywordize-keys kwargs)
        total-years (Integer. (re-find  #"\d+" (query :total-years)))]
    (if (some? total-years)
      (let [mean 2.29   std 1.3
            prob (probablity-density total-years mean std)
            max-proba (/ 0.4 std)
            proba-switching (* (/ prob max-proba) 100)]
        (respond/ok {:success true
                     :proba (str
                             (format "%.2f"  proba-switching) "%")}))
      (respond/bad-request {:success false
                            :message "total-years cant be empty"}))))

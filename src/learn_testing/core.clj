(ns learn-testing.core
  (:gen-class))

(defn addmeup [& args]
  (reduce + args))


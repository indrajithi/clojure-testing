(ns learn-testing.fixtures
  (:require [learn-testing.db.core :as db-core]
            [learn-testing.db.migration :as migration]
            [clojure.tools.logging :as log]
            [config.core :refer [env]]))

(defn delete-database []
  (try
    (db-core/drop-database (:database-name env))
    (log/info "Test database deleted")
    (catch Exception ex
      (log/info "Database not deleted"))))

(defn create-database []
  (try
    (db-core/create-database (:database-name env))
    (log/info "Test database created")
    (catch Exception ex
      (log/info "Test database already created"))))

(defonce ^:private initialized (atom #{}))

(defn has-initialized? [k]
  (contains? @initialized k))


(defn integration-init [f]
  (when-not (has-initialized? :db)
    (try
      (println db-core/datasource-options)
      (println "Initializing database..")
      (delete-database)
      (create-database)
      (log/info "Ran all migrations")
      (migration/migrate)
      (swap! initialized conj :db)
      (log/info "Database initialized.")
      (catch Exception ex
        (.printStackTrace ex)
        (str "caught exception: " (.getMessage ex))
        (swap! initialized conj :db))))
  (f)
  (try
    (catch Exception ex
      (.printStackTrace ex)
      (str "caught exception: " (.getMessage ex))))
  (log/info "database already initialized."))

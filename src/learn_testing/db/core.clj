(ns learn-testing.db.core
  (:require [hikari-cp.core :refer :all]
            [clojure.java.jdbc :as jdbc]
            [config.core :refer [env]]))

(def datasource-options {:auto-commit        true
                         :read-only          false
                         :connection-timeout 30000
                         :validation-timeout 5000
                         :idle-timeout       600000
                         :max-lifetime       1800000
                         :minimum-idle       10
                         :maximum-pool-size  10
                         :pool-name          "db-pool"
                         :adapter            (:database-adapter env)
                         :username           (:database-username env)
                         :password           (:database-password env)
                         :database-name      (:database-name env)
                         :server-name        (:database-host env)
                         :port-number        (:database-port env)
                         :register-mbeans    false})

(defonce datasource
  (delay (make-datasource datasource-options)))

(defn db-jdbc-uri [& {:as args}]
  (let [datasource-options (merge datasource-options args)]
    (format "jdbc:%s://%s:%s/%s?user=%s&password=%s"
            (datasource-options :adapter) (datasource-options :server-name)
            (datasource-options :port-number) (datasource-options :database-name)
            (datasource-options :username) (datasource-options :password))))

(defn execute-insert-query
  "Insert values into db and returns hashmap"
  [sql]
  (jdbc/with-db-connection [conn {:datasource @datasource}]
    (jdbc/execute! conn sql {:return-keys true})))

(defn execute-update-query
  "Update values into db"
  [sql]
  (jdbc/with-db-connection [conn {:datasource @datasource}]
    (jdbc/execute! conn sql)))

(defn execute-select-query [sql]
  (jdbc/with-db-connection [conn {:datasource @datasource}]
    (jdbc/query conn sql {:return-keys true})))

(defn execute-delete-query
  "Delete entry"
  [sql]
  (jdbc/with-db-connection [conn {:datasource @datasource}]
    (jdbc/execute! conn sql)))

(defn truncate [table]
  (jdbc/with-db-connection [conn {:datasource @datasource}]
    (jdbc/execute! conn  [(str "TRUNCATE " table " CASCADE")] {:transaction? false})))

(def jdbc-db-options {:classname "org.postgresql.Driver"
                      :subprotocol "postgresql"
                      :subname (format "//%s/postgres"
                                       (env :database-host))
                      :user (env :database-username)})

(defn create-database [name]
  (jdbc/with-db-connection [conn jdbc-db-options]
    (with-open [s (.createStatement (:connection conn))]
      (.executeUpdate s (format "CREATE DATABASE %s" name)))))

(defn drop-database [name]
  (jdbc/with-db-connection [conn jdbc-db-options]
    (with-open [s (.createStatement (:connection conn))]
      (.executeUpdate s (format "DROP DATABASE %s" name)))))

(defn close-connection []
  (close-datasource @datasource))

(ns learn-testing.db.migration
  (:require [clojure.tools.logging :as log]
            [learn-testing.db.core :refer [db-jdbc-uri]]
            [ragtime.jdbc :as jdbc]
            [ragtime.repl :as ragtime]))

(defn- migration-config []
  {:datastore  (jdbc/sql-database {:connection-uri (db-jdbc-uri)})
   :migrations (jdbc/load-resources "migrations")
   :reporter   (fn [_ op id]
                 (case op
                   :up   (log/info "Applying migration" id)
                   :down (log/info "Rolling back migration" id)))})

(defn migrate []
  (ragtime/migrate (migration-config))
  (log/info "Ran all migrations"))

(defn rollback []
  (ragtime/rollback (migration-config)))

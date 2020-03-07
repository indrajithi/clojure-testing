(defproject learn-testing "0.1.0-SNAPSHOT"
  :description "Learn testing"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [yogthos/config "1.1.7"]
                 [ring/ring-core "1.7.1"]
                 [hikari-cp "2.7.1"]
                 [honeysql "0.9.4"]
                 [ragtime "0.8.0"] ; Database migration library
                 [clj-time "0.15.2"]
                 [ring "1.6.2"]
                 [http-kit "2.4.0-alpha4"]
                 [clj-http-fake "1.0.3"]
                 [metosin/compojure-api "2.0.0-alpha30"]
                 [metosin/ring-http-response "0.9.0"]
                 [org.clojure/tools.logging "0.5.0"]
                 [ch.qos.logback/logback-classic "1.1.1"]
                 [org.clojure/java.jdbc "0.7.9"]
                 [org.postgresql/postgresql "42.2.4"]
                 [ring/ring-jetty-adapter "1.7.1"]]
  :ring {:handler learn-testing/server/-main
         :auto-reload? true
         :open-browser? false
         :reload-paths ["src/" "resources/" "templates/"]}
  :target-path "target/%s"
  :main ^:skip-aot learn-testing.server
  :profiles {:dev {:dependencies [[javax.servlet/javax.servlet-api "3.1.0"]
                                  [ring/ring-mock "0.4.0"]]
                   :plugins [[lein-ring "0.12.5"]
                             [lein-cloverage "1.1.1"]
                             [lein-kibit "0.1.7"]
                             ]
                   :resource-paths ["config/dev" "templates/"
                                    "resources/"]}
             :test {:dependencies [[javax.servlet/javax.servlet-api "3.1.0"]
                                   [ring/ring-mock "0.4.0"]
                                   [org.clojure/test.check "0.10.0-alpha2"]
                                   ]
                    :plugins [[lein-ring "0.12.5"]
                              [lein-cloverage "1.1.1"]]
                    :resource-paths ["config/test"]}
             :uberjar {:aot :all
                       :resource-paths ["config/prod" "templates/"
                                        "resources/"]}})





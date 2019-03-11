(defproject csv2sql "0.1.0-SNAPSHOT"
  :description "A demo of converting a pile of CSVs into Postgres SQL tables."
  :url "http://github.com/ivarthorson/csv2sql"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/data.csv "0.1.4"]
                 [org.clojure/data.json "0.2.6"]
                 [org.clojure/java.jdbc "0.7.9"]
                 [org.postgresql/postgresql "42.2.5"]
                 [clojure.java-time "0.3.2"]]
  :repl-options {:init-ns net.roboloco.csv2sql}
  :main net.roboloco.csv2sql
  :aot [net.roboloco.csv2sql])


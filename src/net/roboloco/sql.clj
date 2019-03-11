(ns net.roboloco.sql
  (:require [clojure.java.jdbc :as sql]))

(def default-db {:dbtype "postgresql" 
                 :dbname "csv2sql"
                 :user   "postgres"
                 :password "postgres"})

(defn connection-ok?
  [db]
  (= {:result 15} (first (sql/query db ["select 3*5 as result"]))))

(defn comma-separate
  [seq]
  (apply str (interpose "," seq)))

(defn table-definition
  "Returns a string schema for creating a table named TABLE-NAME."
  [table-name schema custom-definitions ending-string]
  (let [col-defs (->> schema
                      (sort-by first)
                      (map (fn [[col type]]
                             (if-let [d (get custom-definitions col)]
                               (format "\t%s %s,\n" col d)
                               (format "\t%s %s NULL,\n" col (df/type-to-sqltype type)))))
                      (apply str))]
    (format "CREATE TABLE %s (\n%s %s\n);"
            table-name col-defs ending-string)))

(defn create-table!
  [db table-name schema custom-changes ending-string]
  (let [cmd (table-definition table-name schema custom-changes ending-string)]
    (sql/db-do-commands db cmd)))

(defn drop-table!
  "Drops the timeseries table."
  [db table]
  (let [cmd (format "DROP TABLE %s;" table)]
    (sql/db-do-commands db cmd)))

(defn count-table-rows
  [db table]
  (sql/query db [(format "SELECT COUNT(*) FROM %s" table)]))

(defn table-head
  [db table & [limit]]
  (let [limit (or limit 10)]
    (sql/query db [(format "SELECT * FROM %s LIMIT %d;" table limit)])))

(defn insert-csv
  "Inserts the rows of the CSV into the database. If a column
  is not found in the schema, it is omitted. "
  [db table csvfile schema]
  (let [[columns rows] (df/load-csv-with-types csvfile schema)]
    (sql/insert-multi! db table columns rows)))


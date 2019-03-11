(ns net.roboloco.csvs
  "Functions for loading and saving CSVs."
  (:require [clojure.data.csv :as csv]))

(set! *warn-on-reflection* true)

(defn empty-string-to-nil
  "Returns a nil if given an empty string S, otherwise returns S."
  [s]
  (if (and (string? s) (empty? s))
    nil
    s))

(defn dissoc-nils
  "Drops keys with nil values, or nil keys, from the hashmap H."
  [h]
  (into {} (filter (fn [[k v]] (and v k)) h)))


(defn load-csv
  "Returns a data structure loaded from a CSV file at FILEPATH."
  [filepath]
  (with-open [reader (clojure.java.io/reader filepath)]
    (->> (csv/read-csv reader)
         (map (fn [row] (map empty-string-to-nil row)))
         (doall))))

(defn save-csv
  "Saves a vector of vectors DATA (i.e. a CSV) to disk at FILEPATH. "
  [vec-of-vecs filepath]
  (with-open [writer (clojure.java.io/writer filepath)]
    (csv/write-csv writer vec-of-vecs)))

(defn tabular->maps
  "Converts a vector of vectors into a vector of maps. Assumes that the
  first row of the CSV is a header that contains column names."
  [tabular]
  (let [header (first tabular)]
    (-> (map zipmap (repeat header) (rest tabular))
        (mapv dissoc-nils))))

(defn maps->tabular
  "Converts a vector of vectors into a vector of maps."
  [rowmaps]
  (let [columns (vec (sort (into #{} (map name (flatten (map keys rowmaps))))))]
    (vec (conj (for [row rowmaps]
              (vec (for [col columns]
                     (str (get row col "")))))
            columns))))

(comment

  (def data (tabular->maps (load-csv "/path/to/mycsv.csv")))
  
  (save-csv! (maps->tabular data) "/some/other/path.csv")

  )


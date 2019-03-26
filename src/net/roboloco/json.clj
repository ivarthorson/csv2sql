(ns net.roboloco.json
  "Functions for loading and saving JSONs."
  (:require [clojure.data.json :as js]))

(set! *warn-on-reflection* true)

(defn load-json
  "Returns a data structure loaded from a CSV file at FILEPATH."
  [filepath]
  (with-open [reader (clojure.java.io/reader filepath)]
    (js/read reader)))

(defn save-json
  "Saves a vector of vectors DATA (i.e. a CSV) to disk at FILEPATH. "
  [data filepath]
  (with-open [writer (clojure.java.io/writer filepath)]
    (js/write data writer)))

(defn flatten-keys
  "Given a possibly nested hashmap, flattens it and prepends the names of parent
  keys to child key names, seperated by periods. {A {b 1, c 2}} -> {A.b 1, A.c 2}"
  [h & [prefix]]
  (let [prefix (or prefix [])]
    (loop [h h
           ret {}]
      (if-let [kv (first h)]
        (let [[k v] kv]
          (if (map? v)
            (recur (rest h)
                   (merge ret (flatten-and-concat-keys v (conj prefix k))))
            (recur (rest h)
                   (assoc ret (if (empty? prefix)
                                k
                                (apply str (interpose "." (conj prefix k))))
                          v))))
        ret))))

(defn flat-hashmap-to-tabular
  "Takes an (assumed flat) hashmap, and converts it to tabular format with a single row.
  that is in the same format as a CSV."
  [h]
  (let [columns (vec (sort (keys h)))
        row (mapv #(str (get h % "")) columns)]
    [columns row]))

(defn load-json-as-csv
  "Loads a JSON as if it were a CSV."
  [filepath]
  (->> (load-json filepath)
       (flatten-keys)
       (flat-hashmap-to-tabular)))

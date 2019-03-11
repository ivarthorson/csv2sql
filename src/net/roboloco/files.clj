(ns net.roboloco.files
  (:require [java-time :as jt]
            [clojure.java.io :as jio]))

(defn list-files
  "Lists only the files in the directory string DIR."
  [dir]
  (->> (file-seq (clojure.java.io/file dir))
       (remove #(.isDirectory ^java.io.File %))))

(defn list-subdirectories
  "Lists only the subdirectorys of the directory string DIR"
  [dir]
  (->> (file-seq (clojure.java.io/file dir))
       (filter #(.isDirectory %))
       (remove #(= % (clojure.java.io/file dir)))))

(defn has-suffix?
  "Works on file object types."
  [ ^String suffix ^java.io.File file]
  (and (.isFile file)
       (re-find (re-pattern (str ".*\\." suffix "$")) (.getName file))))

(defn list-files-of-type 
  "Lists all files in the directory with the extension ext."
  [dir ext]
  (->> (file-seq (clojure.java.io/file dir))
       (filter (partial has-suffix? ext))))

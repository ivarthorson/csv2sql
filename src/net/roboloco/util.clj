(ns net.roboloco.util)

(set! *warn-on-reflection* true)

(defn alphanumeric?
  "TRUE when the string is completely alphanumeric."
  [string]
  (= string (apply str (re-seq #"[a-z_A-Z0-9]" string))))

(defn spaces-to-underscores
  "Converts spaces to underscores."
  [string]
  (clojure.string/replace string #"\s" "_"))

(defn periods-to-underscores
  "Converts spaces to underscores."
  [string]
  (clojure.string/replace string #"\." "_"))

(ns net.roboloco.dates
  "Code for handling strings reperesnting dates and datetimes."
  (:require [java-time :as jt]))

(set! *warn-on-reflection* true)

(defn parse-date
  "Parses a standard date, like 2019-02-17."
  [s]
  (jt/local-date "yyyy-MM-dd" s))

(defn parse-datetime
  "Returns the datetime format that Python's pandas usually saves in."
  [s]
  (jt/local-date-time "yyyy-MM-dd HH:mm:ss" s))

(defn local-to-offset
  "Converts a local date time to an offset date time. By default, it assumes
  that the local time is UTC, but you may change this with optional arg TZ."
  [local-date-time & [tz]]
  (let [tz (or tz "UTC")]
    (-> local-date-time
        (jt/zoned-date-time tz)
        (jt/offset-date-time))))

(defn parse-RFC3339
  "Assuming a UTC datestamp with T and Z separator, for example:
  2019-01-17T22:03:16Z
  2019-01-17T22:03:16.383Z
  2019-01-17T22:03:16.111222333Z"
  [s]
  (local-to-offset
   (condp = (count s)
     20 (jt/local-date-time "yyyy-MM-dd'T'HH:mm:ss'Z'" s)
     24 (jt/local-date-time "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" s)
     27 (jt/local-date-time "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'" s)
     30 (jt/local-date-time "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS'Z'" s))))

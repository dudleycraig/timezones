(ns utilities
  (:require [cljsjs.moment]
            [cljsjs.moment-timezone]))

(defn timezones
  "returns a vector of browser supported timezones"
  [] 
  (. js/Intl (supportedValuesOf "timeZone"))) ;; list of timezones supported by browser

(defn local-timezone 
  "returns timezone string local to browser"
  []
  (-> (. js/Intl DateTimeFormat) (. resolvedOptions) (. -timeZone)))

(defn pad 
  "returns a string of two characters, padded with 0 if there aren't enough characters"
  [number]
  (. (str "0" number) (slice -2)))

;; TODO: will probably break when crossing 24 hour timezone transition
(defn overwrite-time 
  "takes a Date object, keeps the date portion and appends a new time"
  [date time]
  (let [h (. date getFullYear)
        m (pad (+ (. date getMonth) 1))
        s (pad (. date getDate))]
    (js/Date. (. js/Date (parse (str h "-" m "-" s "T" time))))))


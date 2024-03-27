(ns components.select
  (:require [cljsjs.moment]
            [cljsjs.moment-timezone]
            [utilities :as utils]))

(defn component [{timezones :timezones 
                  current-timezone :current-timezone 
                  onChange :onChange
                  :or {timezones [] 
                       current-timezone {} 
                       onChange (fn [^js event] (. js/console (log (.. event -target -value))))} 
                  :as props}]
  (into
    [:select
     {:class "timezone-select zone" 
      :value current-timezone
      :onChange onChange}]
    (map
      (fn [timezone] 
        [:option {:value timezone} timezone])
      timezones)))

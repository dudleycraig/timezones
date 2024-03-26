(ns components.clock
  (:require [cljsjs.moment]
            [cljsjs.moment-timezone]
            [utilities :as utils]))

(defn view [{:keys [class hours minutes seconds]}]
  [:span {:class class}
   [:span {:class "hours"} hours]
   [:span {:class "separator"} ":"]
   [:span {:class "minutes"} minutes]
   [:span {:class "separator"} ":"]
   [:span {:class "seconds"} seconds]])

(defn component [{m :moment c :class :or {c "clock"}}]
  (if (instance? js/moment m)
    [view {:class c :hours (utils/pad (. m hour)) :minutes (utils/pad (. m minutes)) :seconds (utils/pad (. m seconds))}]
    [view {:class c :hours "--" :minutes "--" :seconds "--"}]))

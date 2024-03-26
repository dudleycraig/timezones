(ns components.local-view
  (:require [re-frame.core :as rc]
            [cljsjs.moment]
            [cljsjs.moment-timezone]
            [utilities :as utils]
            [components.select :as select]
            [components.clock :as clock]))

(defn component [_]
  (let [{local-moment :local} @(rc/subscribe [:pages.timezones/dynamic-timer])]
    [:div {:class "timezone local"}
     [:label {:class "label"} "local timezone:"]
     [:span {:class "zone"} (utils/local-timezone)]
     [clock/component {:class "clock" :moment local-moment}]]))


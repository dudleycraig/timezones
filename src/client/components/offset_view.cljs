(ns components.offset-view
  (:require [re-frame.core :as rc]
            [cljsjs.moment]
            [cljsjs.moment-timezone]
            [utilities :as utils]
            [components.select :as select]
            [components.clock :as clock]))

(defn component [_]
  (let [current-timezone @(rc/subscribe [:pages.timezones/current-offset-timezone])
        {offset-moment :offset} @(rc/subscribe [:pages.timezones/dynamic-timer])]
    [:div {:class "timezone offset"}
     [:label {:class "label"} "offset timezone:"]
     [select/component {:timezones (utils/timezones)
                        :current-timezone (or current-timezone (utils/local-timezone))
                        :onChange (fn [^js event]
                                    (rc/dispatch [:pages.timezones/current-offset-timezone-set (.. event -target -value)]))}]
     [clock/component {:class "clock" :moment offset-moment}]]))

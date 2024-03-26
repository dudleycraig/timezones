(ns components.static-view
  (:require [re-frame.core :as rc]
            [cljsjs.moment]
            [cljsjs.moment-timezone]
            [utilities :as utils]
            [components.select :as select]
            [components.clock :as clock]))

(defn component [_]
  (let [current-timezone @(rc/subscribe [:pages.timezones/current-static-timezone])
        {input-time :time} @(rc/subscribe [:pages.timezones/static-time])
        date (utils/overwrite-time (js/Date.) input-time)
        input-moment (js/moment. date)
        offset-moment (. (. input-moment clone) (tz current-timezone))]
    [:div {:class "timezone static"}
     [:label {:class "label"} "static timezone:"]
     [select/component {:timezones (utils/timezones)
                        :current-timezone current-timezone
                        :onChange (fn [^js event] 
                                    (rc/dispatch [:pages.timezones/current-static-timezone-set (.. event -target -value)]))}]
     [:input {:class "clock"
              :type "time"
              :step 1
              :onChange (fn [^js event] nil)
              :onBlur (fn [^js event] 
                        (rc/dispatch [:pages.timezones/static-time-set (.. event -target -value)]))}]
     [clock/component {:class "clock" 
                       :moment (if (not= input-time "--:--:--") offset-moment nil)}]]))

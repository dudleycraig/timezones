(ns db
  (:require [cljs.reader]
            [re-frame.core :as rc]
            [cljsjs.moment]
            [cljsjs.moment-timezone]
            [utilities :as utils]))

(def initial-db
  {:current-route :router/part-1 
   :current-offset-timezone (utils/local-timezone)
   :current-static-timezone (utils/local-timezone)
   :dynamic-timer {:local (js/moment.) :offset (js/moment.)}
   :static-time {:time "--:--:--"}})

(rc/reg-event-db
  ::initialize
  (fn [db _] 
    (if db db initial-db)))

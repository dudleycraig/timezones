(ns db
  (:require [cljs.reader]
            [re-frame.core :as rc]
            [cljsjs.moment]
            [cljsjs.moment-timezone]))

(defn local-timezone [] 
  (-> (. js/Intl DateTimeFormat)
      (. resolvedOptions)
      (. -timeZone)))

(def initial-db
  {:current-route nil
   :current-offset-timezone (local-timezone)
   :current-static-timezone (local-timezone)
   :dynamic-timer {:local (js/moment.) :offset (js/moment.)}
   :static-time {:time "--:--:--"}})

(rc/reg-event-db
  ::initialize
  (fn [_ _] 
    initial-db))

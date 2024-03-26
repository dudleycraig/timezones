(ns pages.timezones
  (:require-macros [cljs.core.async.macros :refer [go go-loop alt!]])
  (:require [clojure.string :as str]
            [re-frame.core :as rc]
            [cljsjs.moment]
            [cljsjs.moment-timezone]
            [utilities :as utils]
            [components.clock :as clock]
            [components.select :as select]
            [components.static-view :as static-view]
            [components.offset-view :as offset-view]
            [components.local-view :as local-view]))

(rc/reg-sub ::current-offset-timezone
  (fn [db _]
    (:current-offset-timezone db)))

(rc/reg-event-db ::current-offset-timezone-set
  (fn [db [_ new-timezone]]
    (if (->> (utils/timezones) (filter #(= % new-timezone)) count (< 0)) ;; return truthy if new timezone in timezones
      (assoc db :current-offset-timezone new-timezone)
      (throw (js/Error. (str "No data for timezone " new-timezone))))))

(rc/reg-sub ::dynamic-timer
  (fn [db _]
    (:dynamic-timer db)))

(rc/reg-event-db ::dynamic-timer-set
  (fn [db [_]]
    (let [timezone (get-in db [:current-offset-timezone] (utils/local-timezone))
          local-moment (js/moment.)
          offset-moment (. (. local-moment clone) (tz timezone))]
      (assoc db :dynamic-timer {:local local-moment :offset offset-moment}))))

(defonce do-timer (js/setInterval #(rc/dispatch [::dynamic-timer-set]) 1000))

(rc/reg-sub ::current-static-timezone
  (fn [db _]
    (:current-static-timezone db)))

(rc/reg-event-db ::current-static-timezone-set
  (fn [db [_ new-timezone]]
    (if (->> (utils/timezones) (filter #(= % new-timezone)) count (< 0)) ;; return truthy if new timezone in timezones
      (assoc db :current-static-timezone new-timezone)
      (throw (js/Error. (str "No data for timezone " new-timezone))))))

(rc/reg-sub 
  ::static-time
  (fn [db _]
    (:static-time db)))

(rc/reg-event-db 
  ::static-time-set
  (fn [db [_ static-time]]
    (assoc db :static-time {:time static-time})))

(defn page [_]
  [:div {:id "timezones"}
   [local-view/component]
   [offset-view/component]
   [static-view/component]])

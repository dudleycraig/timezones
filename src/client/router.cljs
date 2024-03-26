(ns router
  (:require [reitit.frontend :as rf]
            [reitit.coercion.spec :as rcs]
            [reitit.frontend.controllers :as rfc]
            [reitit.frontend.easy :as rfe]
            [re-frame.core :as rc]

            [pages.timezones :as timezones]))

(def routes
  ["/"
   ["" {:name ::part-1
        :title "Part 1"
        :handler timezones/page
        :controllers [{:start (fn [] (. js/console (log "entering Timezones")))
                       :stop (fn [] (. js/console (log "exiting Timezones")))}]}]])

(rc/reg-fx 
  :push-state
  (fn [route]
    (apply rfe/push-state route)))

(rc/reg-event-fx
  ::push-state
  (fn [_ [_ & route]]
    {:push-state route}))

(rc/reg-event-db
  ::navigated
  (fn [db [_ new-match]]
    (let [old-match (:current-route db)
          controllers (rfc/apply-controllers (:controllers old-match) new-match)]
      (assoc db :current-route (assoc new-match :controllers controllers)))))

(rc/reg-sub
  ::current-route
  (fn [db]
    (:current-route db)))

(defn href
  ([k]
   (href k nil nil))
  ([k params]
   (href k params nil))
  ([k params query]
   (rfe/href k params query)))

(defn on-navigate [new-match]
  (when new-match
    (rc/dispatch [::navigated new-match])))

(def router
  (rf/router
    routes
    {:data {:coercion rcs/coercion}}))

(defn init []
  (rfe/start!
    router
    on-navigate 
    {:use-fragment false}))

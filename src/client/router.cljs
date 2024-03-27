(ns router
  (:require [reitit.frontend :as rf]
            [reitit.coercion.spec :as rcs]
            [reitit.frontend.controllers :as rfc]
            [reitit.frontend.easy :as rfe]
            [re-frame.core :as rc]
            [pages.timezones :as timezones]))

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

(def routes
  ["/"
   ["" {:name ::part-1
        :title "Part 1"
        :view timezones/page
        :controllers [{:start (fn [& params] (. js/console (log "entering ::part-1")))
                       :stop (fn [& params] (. js/console (log "leaving ::part-1")))}]}]])

(defn on-navigate [new-match]
  (when new-match
    (rc/dispatch [::navigated new-match])))

(def router
  (rf/router
    routes
    {:data {:coercion rcs/coercion}}))

(defn init! []
  (rfe/start!
    router
    on-navigate 
    {:use-fragment true}))






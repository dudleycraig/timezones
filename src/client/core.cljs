(ns core
  (:require [reagent.dom :as rd]
            [re-frame.core :as rc]
            [router :as router]
            [components.page :as page]
            [db :as db]))

(defn mount-ui []
  (router/init)
  (rd/render
    [page/component {:router router/router}]
    (. js/document getElementById "main")))

(defn ^:dev/after-load clear-cache-and-render! 
  []
  (rc/clear-subscription-cache!)
  (mount-ui))

(defn start
  []
  (rc/dispatch-sync [:db/initialize])
  (mount-ui))

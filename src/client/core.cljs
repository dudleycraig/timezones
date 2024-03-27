(ns core
  (:require [reagent.dom :as rd]
            [re-frame.core :as rc]
            [router :as router]
            [components.page :as page]
            [db]))

(defn mount-client []
  (when-let [client (. js/document getElementById "client")]
    (rd/render
      [page/component {:router router/router}]
      client)))

(defn ^:export render!
  "initial render of client"
  []
  (rc/dispatch-sync [:db/initialize])
  (rc/clear-subscription-cache!)
  (router/init!)
  (mount-client))

(defn ^:dev/after-load re-render
  "re-render of client"
  []
  (mount-client))



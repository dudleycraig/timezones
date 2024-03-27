(ns components.page
  (:require [re-frame.core :as rc]
            [components.stage :as stage]
            [components.header-bar :as header-bar]
            [components.main-navigation :as main-navigation]
            [components.page-header :as page-header]))

(defn component [router]
  (let [current-route @(rc/subscribe [:router/current-route]) 
        route-view (-> current-route :data :view)]
    [stage/component
     [header-bar/component
      [main-navigation/component router]]
     [page-header/component router]
     (when current-route [route-view])]))

(ns components.page-header
  (:require [re-frame.core :as rc]
            [reitit.core :as rec]
            [router :as router]))

(defn component [{:keys [router]}]
  (let [current-route @(rc/subscribe [:router/current-route])
        title (-> current-route :data :title)]
    [:h1
     {:class "page-header"
      :style {:font-size "2em"
              :margin "10px 30px"}}
     (str title)]))

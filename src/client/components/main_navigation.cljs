(ns components.main-navigation
  (:require [reitit.core :as rec]
            [reitit.frontend.easy :as rfe]
            [re-frame.core :as rc]
            [router :as router]))

(def active-nav 
  "active disabled:text-black/30 lg:px-2 [&.active]:text-black/90 
  dark:[&.active]:text-neutral-400")

(def inactive-nav 
  "p-0 text-neutral-500 transition duration-200 hover:text-neutral-700 
  hover:ease-in-out focus:text-neutral-700 disabled:text-black/30 
  motion-reduce:transition-none dark:text-neutral-200 dark:hover:text-neutral-400 
  dark:focus:text-neutral-400 lg:px-2 [&.active]:text-black/90 dark:[&.active]:text-neutral-400")

(defn component [{:keys [router]}]
  (let [current-route @(rc/subscribe [:router/current-route])]
    [:ul {:class "list-style-none mr-auto flex flex-col pl-0 lg:mt-1 lg:flex-row"}
     (for [route-name (rec/route-names router)
           :let [route (rec/match-by-name router route-name)
                 title (-> route :data :title)]]
       [:li
        {:key route-name
         :class (if (= route-name (-> current-route :data :name)) active-nav inactive-nav)}
        [:a {:href (router/href route-name)}
         title]])]))

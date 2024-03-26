(ns router
  (:require [clojure.tools.logging :as log]
            [ring.middleware.params :as params]
            [reitit.ring :as ring]
            [reitit.ring.coercion :as coercion]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [muuntaja.core :as m]))

(defn routes []
  (ring/ring-handler
    (ring/router
      [["/"
        ["" {:get (fn [_] {:status 200 :body {:message "home response"}})}]]]

      {:data
       {:muuntaja m/instance
        :middleware [params/wrap-params
                     muuntaja/format-middleware
                     coercion/coerce-exceptions-middleware
                     coercion/coerce-request-middleware
                     coercion/coerce-response-middleware]}})
    (ring/create-default-handler)
    (ring/routes
      (ring/create-file-handler {:root "public" :path "/"})
      (ring/create-default-handler {:not-found (constantly {:status 404, :body "Not Found"})}))))

(comment
  (do
    (require '[integrant.repl :as ig-repl]
             '[integrant.repl.state :as state]))

  (let [routing (-> state/system :main/routing)
        {status :status body :body} (routing {:uri "/" :request-method :get :headers {:accept "application/json"}})]
    (println "status: " status ", body: " body)))

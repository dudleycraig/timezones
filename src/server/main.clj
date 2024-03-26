(ns main
  (:require [integrant.core :as ig]
            [clojure.tools.logging :as log]
            [ring.adapter.jetty :as jetty]
            [router :as router]))

(def config
  {::http {:handler (ig/ref ::routing) :port 3000}
   ::routing {}})

(defmethod ig/init-key ::http [_ {:keys [handler port]}]
  (jetty/run-jetty handler {:port port :join? false}))

(defmethod ig/init-key ::routing [_ _]
  (router/routes))

(defmethod ig/halt-key! ::http [_ server]
  (.stop server))

(defn -main []
  (ig/init config))

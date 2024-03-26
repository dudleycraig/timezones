(ns user
  (:require [integrant.repl :as ig-repl]
            [integrant.repl.state :as state]
            [main :as main]))

(ig-repl/set-prep!
 (fn [] main/config))

(def go ig-repl/go)
(def halt ig-repl/halt)
(def reset ig-repl/reset)
(def reset-all ig-repl/reset-all)

(def http (-> state/system :main/http))
(def routing (-> state/system :main/routing))

(comment
  (halt)
  (reset)
  (reset-all)
  (go))

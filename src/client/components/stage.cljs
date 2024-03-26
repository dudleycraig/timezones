(ns components.stage)

(defn component [& children]
  ^{:key (str "stage-component")}
  (into 
    [:div
     {:key "stage"
      :class "stage"
      :style {:display "flex"
              :flex-grow 1
              :flex-direction "column"
              :flex-wrap "nowrap"
              :justify-content "flex-start"
              :align-items "flex-start"
              :align-content "flex-start"
              :background "#f0f0f0"}}]
    children))

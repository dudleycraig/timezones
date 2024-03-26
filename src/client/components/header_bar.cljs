(ns components.header-bar)

(defn component [& children]
  ^{:key "header-bar-component"} 
  (into 
    [:div {:class "relative flex w-full flex-nowrap items-center justify-between bg-[#FBFBFB] py-2 text-neutral-500 shadow-lg hover:text-neutral-700 focus:text-neutral-700 dark:bg-neutral-600 lg:flex-wrap lg:justify-start lg:py-4"}]
    children))

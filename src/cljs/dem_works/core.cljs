(ns dem-works.core
    (:require [reagent.core :as reagent]
              [re-frame.core :as re-frame]
              [dem-works.events]
              [dem-works.subs]
              [dem-works.routes :as routes]
              [dem-works.views :as views]
              [dem-works.config :as config]))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (routes/app-routes)
  (re-frame/dispatch-sync [:initialize-db])
  (dev-setup)
  (mount-root))

(ns dem-works.events
    (:require [re-frame.core :as re-frame]
              [dem-works.db :as db]
              [ajax.core :as ajax :refer [GET POST PUT]]))

(defn log-service-error [resp]
  (.log js/console (str "something bad happened: " resp)))

(defn log-service-success [response]
  (.log js/console (str response)))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))


(re-frame/reg-event-db
 :update-todos
 (fn [db [_ newtodos]]
   (assoc db :todos (js->clj newtodos))))

(re-frame/reg-event-fx
 :add-todo
 (fn [db [event val]]
   (PUT (str "/add-todo/" val) { :error-handler log-service-error
                       :handler #(re-frame/dispatch [:update-todos %1])
                       :response-format (ajax/json-response-format
                                         {:keywords? true})})))

(re-frame/reg-event-db
 :set-active-panel
 (fn [db [_ active-panel]]
   (assoc db :active-panel active-panel)))

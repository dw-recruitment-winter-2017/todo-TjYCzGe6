(ns dem-works.events
    (:require [re-frame.core :as re-frame]
              [dem-works.db :as db]))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/reg-event-db
 :add-todo
 (fn [db [_ new-todo-text]]
   (assoc-in db [:todos (count (:todos db))] {:id (count (:todos db)) :text new-todo-text :done false})))

(re-frame/reg-event-db
 :set-active-panel
 (fn [db [_ active-panel]]
   (assoc db :active-panel active-panel)))

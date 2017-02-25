(ns dem-works.views
    (:require [re-frame.core :as re-frame]))


;; home


(defn render-todo [{:keys [id text done]}]
  [:div
   [:input {:type "checkbox"
            :checked done}]
   [:label text]])

(defn home-panel []
  (let [todos (re-frame/subscribe [:todos])]
    [:div
        [:div "Click here to"
        [:a {:href "#/about"} "go to About Page"]]     
       [:div "Or, type in text and press enter to create a Todo."]
       [:input {:type "text"
                :id "todoInput"
                :on-key-down #(case (.-which %)
                                13 (re-frame/dispatch [:add-todo (-> % .-target .-value)])
                                nil)}]
      (if (> (count @todos) 0)
        (for [todo (vals @todos)]
          (render-todo todo)))
       ]))


;; about

(defn about-panel []
  (fn []
    [:div "This is the About Page."
     [:div [:a {:href "#/"} "go to Home Page"]]]))


;; main

(defn- panels [panel-name]
  (case panel-name
    :home-panel [home-panel]
    :about-panel [about-panel]
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [:active-panel])]
    (fn []
      [:h2 "TODO MVC APP"]
      [show-panel @active-panel])))

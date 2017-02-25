(ns dem-works.views
    (:require [re-frame.core :as re-frame]))


;; home


(defn render-todo [{:keys [id text done]}]
  [:div {:key id}
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
                                13 (re-frame/dispatch [:add-todo
                                                       (-> % .-target .-value)])
                                nil)}]
      (if (> (count @todos) 0)
        (for [todo (vals @todos)]
         (render-todo todo)))
       ]))


;; about

(defn about-panel []
  (fn []
    [:div "This is the About Page."
     [:div [:a {:href "#/"} "go to Home Page"]]
     [:div "So far I have implemented the abbility to add todos, and see the todos you have added."]
     [:div "This project consists of a reaganet and re-frame front end
that talks to the back end via cljs-ajax.  It makes requests just using the query string 
parsing abilities of Ring."]

     [:div "I don't necessarily feel that this is the optimal way to go, but I spent about 
2 of my 4 hours trying to get Liberator to work.  I could do a GET, but I didn't figure 
out how to get the params of my JSON PUT request to come through.  My best guesses right now
are that they were were either encoded as a Bytestream on the body of the request, or they 
just weren't getting passed in from the front end at all.

 Anyways, I wanted to keep to the time limits you guys assigned and just reached a point where it was just time to move on.
If I had this to do over again, I would probably try composure-api :)"]
     [:div "I tried to show a little of my knowledge of re-frame in the time remaining by
using both reg-event-fx and reg-event-db."]
     [:div "My next TODOS would be to:"
      [:ul
       [:li "Obviously finish out the features in the project description"]
       [:li "Make it work with JSON instead of query strings"]
       [:li "Even though it's not really needed for these simple todos, implement some validations with core.spec, or Schema"]
       [:li "I like stewart sierra's Component architecture.  If i had a bunch of time, I would re-structure the app to follow his patterns."]
       [:li "Styling"]]
      ]
     [:div "As you may be able to tell from this About section, this was my first time making
a SPA in Clojure :).  Thanks for the project, it was 
fun and I learned a bit on the way."]]))


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

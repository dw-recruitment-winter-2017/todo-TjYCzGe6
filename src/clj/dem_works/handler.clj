(ns dem-works.handler
  (:require [compojure.core :refer [GET PUT defroutes]]
            [compojure.route :refer [resources]]
            [ring.util.response :refer [resource-response]]
            [ring.middleware.reload :refer [wrap-reload]]
            [clojure.data.json :as json]
            [liberator.core :refer [defresource]]
            [ring.middleware.params :refer [wrap-params]]
            [dem-works.db :as db]
            [cheshire.core :refer :all]))


;(defresource add-todo [ctx]
;  :available-media-types ["application/json"]
;  :allowed-methods [:put]
;  :put! (fn [ctx] (let [rq (:request ctx)
;                        params (:params rq)]
;                    (db/add-todo (:text params))))
;  :handle-created (fn [_] json/write-json (db/get-all-todos)))

(defn add-todo [text]
          (do
           (db/add-todo text)
           (generate-string (db/get-all-todos))))

(defroutes routes
  (GET "/" [] (resource-response "index.html" {:root "public"}))
  (PUT "/add-todo/:text" [text] (add-todo text))
  (resources "/"))

(def dev-handler (-> #'routes wrap-reload))

(def handler (-> routes wrap-params))

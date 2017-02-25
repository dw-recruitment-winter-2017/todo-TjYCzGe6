(ns dem-works.db)

(defonce todos (atom {}))

(defn get-all-todos []
  @todos)

(defn delete-todo [id]
  (swap! todos dissoc id))

(defn toggle-todo-completion [id]
  (swap! todo update-in [id :done?] (complement (get-in @todos [id :done?]))))

(defn clear-todos []
  (swap! todos {}))

(defn add-todo [newtext]
  (let [newid (if (= 0 (count @todos))
                0
                (inc (apply max (keys @todos))))]
    (swap! todos assoc newid {:id newid :text newtext :done? false})))

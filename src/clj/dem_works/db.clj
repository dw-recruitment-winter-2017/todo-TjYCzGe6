(ns dem-works.db)

;An in-memory DB for the todos

(defonce todos (atom {}))

(defn get-all-todos []
  @todos)

(defn delete-todo [id]
  (swap! todos dissoc id))

(defn toggle-todo-completion [id]
  (swap! todos update-in [id :done] not))

(defn clear-todos []
  (swap! todos {}))

(defn add-todo [newtext]
  (let [newid (if (= 0 (count @todos))
                0
                (inc (apply max (keys @todos))))]
    (swap! todos assoc newid {:id newid :text newtext :done false})))

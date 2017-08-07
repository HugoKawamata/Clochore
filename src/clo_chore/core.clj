(ns clo-chore.core
  (:gen-class))

(require '[clojure.string :as str])

(def file "clochore_saved_chores.txt")

(defn print-args [args]
  (doseq [arg args]
    (println (str "arg=" arg))))

(defn splitFile [fname]
  "Split save file into a vector containing two vectors
  containing each chore and expiry"
  (map #(str/split % #",") (str/split (str/replace 
    (slurp fname) #"Chore:|Expiry:" "") #"\n")))

(defn joinFile [fileVector]
  (let [halfbaked (map #(str/join "," %) fileVector)]
    (str/join "\n"
      (vector
        (str "Chore:" (first halfbaked))
        (str "Expiry:" (second halfbaked))))))

(defn addChore [chore date]
  (let [fileVector (splitFile file)]
    (vector (conj (first fileVector) chore)
            (second fileVector))))

(defn -main
  [& args]
  (spit file "Chore:Name,name\nExpiry:Jeff,jeff")
  (println (joinFile (addChore "newchore" "1/2/3")))
  (flush)
)

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

(defn addChore [chore date fileVector]
  (vector (conj (first fileVector) (str chore "|" date))
          (second fileVector)))

(defn addExpiry [expiry date fileVector]
  (vector (first fileVector)
          (conj (second fileVector) (str expiry "|" date))))

(defn removeChoreOrExpiry [ctype cname fileVector]
  (case (str/lower-case ctype)
    "chore" (vector (remove #(= cname %) (first fileVector)) (second fileVector))
    "expiry" (vector (first fileVector) (remove #(= cname %) (second fileVector)))))

(defn -main
  ([mode ctype cname date]
  (case (str/lower-case mode)
    "add"
      (case (str/lower-case ctype)
        "chore"
          (spit file (joinFile (addChore cname date (splitFile file))))
        "expiry"
          (spit file (joinFile (addExpiry cname date (splitFile file))))
      )
  ))

  ([mode ctype cname]
  (case (str/lower-case mode)
    "remove"
      (spit file (joinFile (removeChoreOrExpiry ctype cname (splitFile file))))
  ))
)

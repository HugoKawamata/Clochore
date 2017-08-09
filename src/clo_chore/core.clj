(ns clo-chore.core
  (:gen-class))

(require '[clojure.string :as str])
(require '[clj-time :as t])

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

(defn addChoreOrExpiry [cname date fileVector]
  (case (str/lower-case ctype)
    "chore" (vector (conj (first fileVector) (str cname "|" date))
              (second fileVector))
    "expiry" (vector (first fileVector)
               (conj (second fileVector) (str cname "|" date))))

(defn removeChoreOrExpiry [ctype cname fileVector]
  (case (str/lower-case ctype)
    "chore" (vector (remove #(= cname %) (first fileVector)) (second fileVector))
    "expiry" (vector (first fileVector) (remove #(= cname %) (second fileVector)))))

(defn validMode? [mode]
  (or (= mode "add") (= mode "remove")))

(defn validCType? [ctype]
  (or (= mode "chore") (= mode "expiry")))

(defn validCName? [cname]
  (not (or (.contains cname ",") (.contains cname "|") (.contains cname "\n"))))

(defn validDate? [date]

  )


(defn -main
  ([mode ctype cname date]
  (case (str/lower-case mode)
    "add"
      (spit file (joinFile (addChoreOrExpiry cname date (splitFile file))))
  ))

  ([mode ctype cname]
  (case (str/lower-case mode)
    "remove"
      (spit file (joinFile (removeChoreOrExpiry ctype cname (splitFile file))))
  ))
)

(ns clo-chore.core
  (:gen-class))

(defn print-args [args]
  (doseq [arg args]
    (println (str "arg=" arg))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (print-args args)
  (flush)
  (print-args ["a" "b" "c"])
  (+ 1 0)
)

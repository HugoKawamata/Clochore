(defproject clo-chore "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot clo-chore.core
  :target-path "target/%s"
  :plugins [[lein-bin "0.3.5"]]
  :bin { :name "runme"
         :bin-path "target" }
  :profiles {:uberjar {:aot :all}})

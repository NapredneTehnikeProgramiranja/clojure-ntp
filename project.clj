(defproject clojure-ntp "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha16"]
                 [org.clojure/java.jdbc "0.7.0-alpha3"]]
  :main ^:skip-aot clojure-ntp.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})

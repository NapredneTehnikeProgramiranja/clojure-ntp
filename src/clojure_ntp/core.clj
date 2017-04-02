(ns clojure-ntp.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn say-hello [name]
  (println (str "Hello " name)))

(say-hello "Pera")
;; => Hello  Pera

(defn square [a] (* a a))
(defn sum-of-squares [a b]
  (+ (square a) (square b)))

(= (sum-of-squares 2 5)
   (+ (square 2) (square 5))
   (+ 4 25))

(ns clojure-ntp.partials
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.repl :refer :all]
            [clojure.string :as str]))

(doc partial)
;; =>
;; -------------------------
;; clojure.core/partial
;; ([f] [f arg1] [f arg1 arg2] [f arg1 arg2 arg3] [f arg1 arg2 arg3 & more])
;; Takes a function f and fewer than the normal arguments to f, and
;; returns a fn that takes a variable number of additional args. When
;; called, the returned function calls f with args + additional args.
;; nil

(def mul3 (partial * 3))

(mul3 2)
;; => 6

(doc do)
;; =>
;; -------------------------
;; do
;; (do exprs*)
;; Special Form
;; Evaluates the expressions in order and returns the value of
;; the last. If no expressions are supplied, returns nil.

;; Please see http://clojure.org/special_forms#do
;; nil

#_(do
    (def spec {:classname   "org.posgresql.Driver"
               :subprotocol "postgresql"
               :subname     "//localhost:5453/our_little_database"})

    (defn all-users []
      (jdbc/query spec ["select * from login order by username desc"]))

    (defn find-user [username]
      (jdbc/query spec ["select * from login where username = ?" username]))

    (defn create-user [username password]
      (jdbc/insert! spec :login {:username username :password password
                                 :sal      "some_salt"})))

#_(do
    (def spec {:classname   "org.posgresql.Driver"
               :subprotocol "postgresql"
               :subname     "//localhost:5453/our_little_database"})

    (def query (partial jdbc/query spec))
    (def insert! (partial jdbc/insert! spec))

    (defn all-users []
      (jdbc/query ["select * from login order by username desc"])

      (defn find-user [username]
        (jdbc/query ["select * from login where username = ?" username]))

      (defn create-user [username password]
        (jdbc/insert! :login {:username username :password password
                              :sal      "some_salt"}))))

(defn apply-sales-tax [items]
  (map (partial * 1.6) items))

(apply-sales-tax [0.23 0.12 8.12 4.2])
;; => (0.36800000000000005 0.192 12.991999999999999 6.720000000000001)

(defn a-result [items]
  (map * items))

(a-result [0.23 0.12 8.12 4.2])
;; => (0.23 0.12 8.12 4.2)

(map (partial - 2) [1 2 3])
;; => (1 0 -1)

(map #(- % 2) [1 2 3])
;; => (-1 0 1)

(defn minify [input]
  (clojure.string/join
   (map clojure.string/trim
        (clojure.string/split-lines input))))

(minify "javascriptcode javascriptcode    \njavascriptcodejavascriptco   \n")
;; => javascriptcode javascriptcodejavascriptcodejavascriptco

(defn minify2 [input]
  (-> clojure.string/trim
      (map (clojure.string/split-lines input))
      clojure.string/join))

(minify2 "javascriptcode javascriptcode    \njavascriptcodejavascriptco   \n")
;; => "javascriptcode javascriptcodejavascriptcodejavascriptco"

(def result (map #(do (println ".") (inc %)) [0 1 2 3]))

result
;; =>
;; .
;; .
;; .
;; .
;; (1 2 3 4)

(def fib-seq
  (lazy-cat [1 1] (map + (rest fib-seq) fib-seq)))

(doc lazy-cat)
;; =>
;; -------------------------
;; clojure.core/lazy-cat
;; ([& colls])
;; Macro
;; Expands to code which yields a lazy sequence of the concatenation
;; of the supplied colls.  Each coll expr is not evaluated until it is
;; needed.

;; (lazy-cat xs ys zs) === (concat (lazy-seq xs) (lazy-seq ys) (lazy-seq zs))
;; nil

(take 10 fib-seq)
;; => (1 1 2 3 5 8 13 21 34 55)

(doc take)
;; =>
;; -------------------------
;; clojure.core/take
;; ([n] [n coll])
;; Returns a lazy sequence of the first n items in coll, or all items if
;; there are fewer than n.  Returns a stateful transducer when
;; no collection is provided.
;; nil

(def people ["Milica" "Milan" "Ivan" "Stefan" "Nikolina" "Nikola" "Ilija" "Dunja"
             "Višnja" "Aida" "Nemanja" "Ivona" "Petar" "Petra" "Aagda" "Dušan"])

(mapv #(vector %1 %2) (cycle [:up :down :left :right]) people)
;; =>
;; [[:up "Milica"]
;;  [:down "Milan"]
;;  [:left "Ivan"]
;;  [:right "Stefan"]
;;  [:up "Nikolina"]
;;  [:down "Nikola"]
;;  [:left "Ilija"]
;;  [:right "Dunja"]
;;  [:up "Višnja"]
;;  [:down "Aida"]
;;  [:left "Nemanja"]
;;  [:right "Ivona"]
;;  [:up "Petar"]
;;  [:down "Petra"]
;;  [:left "Aagda"]
;;  [:right "Dušan"]]

(doc mapv)
;; =>
;; -------------------------
;; clojure.core/mapv
;; ([f coll] [f c1 c2] [f c1 c2 c3] [f c1 c2 c3 & colls])
;; Returns a vector consisting of the result of applying f to the
;; set of first items of each coll, followed by applying f to the set
;; of second items in each coll, until any one of the colls is
;; exhausted.  Any remaining items in other colls are ignored. Function
;; f should accept number-of-colls arguments.
;; nil

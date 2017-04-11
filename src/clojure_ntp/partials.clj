(ns clojure-ntp.partials
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.repl :refer :all]))

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

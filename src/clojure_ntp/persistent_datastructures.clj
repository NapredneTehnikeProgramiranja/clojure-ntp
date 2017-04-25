(ns clojure-ntp.persistent-datastructures
  "This code is borrowed from the book Professional Clojure."
  (:require [clojure.repl :refer [doc]]))

(defprotocol INode
  (entry [_])
  (left [_])
  (right [_])
  (contains-value? [_ _])
  (insert-value [_ _]))

(deftype Node [value left-branch right-branch]
  INode
  (entry [_] value)
  (left [_] left-branch)
  (right [_] right-branch)
  (contains-value? [tree v]
    (cond
      (= v value) true
      (< v value) (contains-value? left-branch v)
      (> v value) (contains-value? right-branch v)))
  (insert-value [tree v]
    (cond
      (= v value) tree
      (< v value) (Node. value (insert-value left-branch v) right-branch)
      (> v value) (Node. value left-branch (insert-value right-branch v)))))

(extend-protocol INode
  nil
  (entry [_] nil)
  (left [_] nil)
  (right [_] nil)
  (contains-value? [_ _] false)
  (insert-value [_ value] (Node. value nil nil)))

(def root (->Node 7 nil nil))
(left root)
;; => nil
(right root)
;; => nil
(entry root)
;; => 7
(contains-value? root 7)
;; => true
(contains-value? root 23)
;; => false

(def root (Node. 7 (Node. 5 (Node. 3 nil nil) nil)
                 (Node. 12 (Node. 9 nil nil) (Node. 17 nil nil))))

(left root)
;; => #object[clojure_ntp.persistent_datastructures.Node 0x5c0500d7
;;            "clojure_ntp.persistent_datastructures.Node@5c0500d7"]

(entry (left root))
;; => 5

(entry (left (left root)))
;; => 3

(entry (right root))
;; => 12

(-> root
    right
    left
    entry)
;; => 9

(doc identity)
;; =>
;; -------------------------
;; clojure.core/identity
;; ([x])
;; Returns its argument.
;; nil

(identity (left root))
;; => #object[clojure_ntp.persistent_datastructures.Node 0x5c0500d7
;;            "clojure_ntp.persistent_datastructures.Node@5c0500d7"]

(identity (right root))
;; => #object[clojure_ntp.persistent_datastructures.Node 0x45a2d58d
;;            "clojure_ntp.persistent_datastructures.Node@45a2d58d"]

(def l (insert-value root 6))

(identity (left l))
;; => #object[clojure_ntp.persistent_datastructures.Node 0x12b6354b
;;            "clojure_ntp.persistent_datastructures.Node@12b6354b"]
(entry (left l))
;; => 5

(identity (right l))
;; => #object[clojure_ntp.persistent_datastructures.Node 0x45a2d58d
;;            "clojure_ntp.persistent_datastructures.Node@45a2d58d"]
(entry (right l))
;; => 12

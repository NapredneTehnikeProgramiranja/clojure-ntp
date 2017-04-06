(ns clojure-ntp.exercises-1
  (:require [clojure.repl :refer [doc]]))

;; get-the-caps
;; ----------------------------------------------------------------------

(doc ->>)
;; =>
;; -------------------------
;; clojure.core/->>
;; ([x & forms])
;; Macro
;; Threads the expr through the forms. Inserts x as the
;; last item in the first form, making a list of it if it is not a
;; list already. If there are more forms, inserts the first form as the
;; last item in second form, etc.
;; nil

(doc apply)
;; =>
;; -------------------------
;; clojure.core/apply
;; ([f args] [f x args] [f x y args] [f x y z args] [f a b c d & args])
;; Applies fn f to the argument list formed by prepending intervening arguments to args.
;; nil

(defn get-the-caps
  "Gets the string and returns string consisted of capital letters from
  the original."
  [s]
  (let [capitals (->> (range 65 91)
                      (map char)
                      set)]
    (apply str (filter capitals s))))

(get-the-caps "AmsdaBsdaDsdaDAS")
;; => "ABDDAS"

;; drop-nth
;; ----------------------------------------------------------------------

(doc conj)
;; =>
;; -------------------------
;; clojure.core/conj
;; ([coll x] [coll x & xs])
;; conj[oin]. Returns a new collection with the xs
;; 'added'. (conj nil item) returns (item).  The 'addition' may
;; happen at different 'places' depending on the concrete type.
;; nil

(defn drop-nth [v n]
  (loop [v   v
         acc []]
    (if (> n (count v))
      acc
      (recur (drop n v)
             (->> (dec n) (nth v) (conj acc))))))

(drop-nth (range 10) 2)
;; => [1 3 5 7 9]

(drop-nth (range 10) 3)
;; => [2 5 8]

(drop-nth (range 10) 5)
;; => [4 9]

(drop-nth (range 10) 10)
;; => [9]

(drop-nth (range 10) 11)
;; => []

(doc ->)
;; =>
;; -------------------------
;; clojure.core/->
;; ([x & forms])
;; Macro
;; Threads the expr through the forms. Inserts x as the
;; second item in the first form, making a list of it if it is not a
;; list already. If there are more forms, inserts the first form as the
;; second item in second form, etc.
;; nil

(doc keep-indexed)
;; =>
;; -------------------------
;; clojure.core/keep-indexed
;; ([f] [f coll])
;; Returns a lazy sequence of the non-nil results of (f index item). Note,
;; this means false return values will be included.  f must be free of
;; side-effects.  Returns a stateful transducer when no collection is
;; provided.
;; nil

(defn drop-nth-2 [v n]
  (keep-indexed (fn [i v] (if (-> i
                                  inc
                                  (mod n)
                                  zero?)
                            v))
                v))

(drop-nth-2 (range 10) 2)
;; => (1 3 5 7 9)

(drop-nth-2 (range 10) 3)
;; => (2 5 8)

(drop-nth-2 (range 10) 5)
;; => (4 9)

(drop-nth-2 (range 10) 10)
;; => (9)

(drop-nth-2 (range 10) 11)
;; => ()

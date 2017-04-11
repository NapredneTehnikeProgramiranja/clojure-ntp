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

(doc concat)
;; =>
;; -------------------------
;; clojure.core/concat
;; ([] [x] [x y] [x y & zs])
;; Returns a lazy seq representing the concatenation of the elements in the supplied colls.
;; nil

(doc into)
;; =>
;; -------------------------
;; clojure.core/into
;; ([to from] [to xform from])
;; Returns a new coll consisting of to-coll with all of the items of
;; from-coll conjoined. A transducer may be supplied.
;; nil

(defn drop-nth
  "Drop every nth element from a vector starting with first if n fits
  in length of v."
  [v n]
  (loop [v   v
         acc []]
    (if (> n (count v))
      (into [] acc)
      (recur (drop n v)
             (->> v (take (dec n)) (concat acc))))))

(drop-nth (range 10) 2)
;; => [0 2 4 6 8]

(drop-nth (range 10) 3)
;; => [0 1 3 4 6 7]

(drop-nth (range 10) 5)
;; => [0 1 2 3 5 6 7 8]

(drop-nth (range 10) 10)
;; => [0 1 2 3 4 5 6 7 8]

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

(defn drop-nth-2
  "Drop every nth element from a vector starting with first if n fits
  in length of v."
  [v n]
  (if (>= (count v) n)
    (vec
     (keep-indexed (fn [i v] (if (-> i
                                     (mod n)
                                     zero?
                                     not)
                               v))
                   v))
    []))

(drop-nth-2 (range 10) 2)
;; => [1 3 5 7 9]

(drop-nth-2 (range 10) 3)
;; => [1 2 4 5 7 8]

(drop-nth-2 (range 10) 5)
;; => [1 2 3 4 6 7 8 9]

(drop-nth-2 (range 10) 10)
;; => [1 2 3 4 5 6 7 8 9]

(drop-nth-2 (range 10) 11)
;; => []

(defn drop-nthh
  "Drop every n-th element using only take-nth and a little bit of
  type theory."
  [v n]
  (if (>= (count v) n)
    (vec (clojure.set/difference (apply sorted-set v) (take-nth n v)))
    []))

(drop-nthh (range 10) 2)
;; => [1 3 5 7 9]

(drop-nthh (range 10) 3)
;; => [1 2 4 5 7 8]

(drop-nthh (range 10) 5)
;; => [1 2 3 4 6 7 8 9]

(drop-nthh (range 10) 10)
;; => [1 2 3 4 5 6 7 8 9]

(drop-nthh (range 10) 11)
;; => []

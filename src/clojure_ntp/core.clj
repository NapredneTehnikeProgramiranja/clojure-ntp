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

(defn fib [n]
  (cond
    (= n 0) 0
    (= n 1) 1
    :else (+ (fib (- n 1))
             (fib (- n 2)))))

(def memo-fib (memoize fib))

(time (fib 23))
;; =>
;; "Elapsed time: 30.039186 msecs"
;; 28657

(time (memo-fib 23))
;; =>
;; "Elapsed time: 23.083033 msecs"
;; 28657

(time (memo-fib 23))
;; =>
;; "Elapsed time: 0.246522 msecs"
;; 28657

(doc memoize)
;; =>
;; -------------------------
;; clojure.core/memoize
;; ([f])
;; Returns a memoized version of a referentially transparent function. The
;; memoized version of the function keeps a cache of the mapping from arguments
;; to results and, when calls with the same arguments are repeated often, has
;; higher performance at the expense of higher memory use.
;; nil

(defn factorial [n]
  (case n
    (0 1) 1
    (* n (factorial (- n 1)))))

(doc recur)
;; =>
;; -------------------------
;; recur
;; (recur exprs*)
;; Special Form
;; Evaluates the exprs in order, then, in parallel, rebinds
;; the bindings of the recursion point to the values of the exprs.
;; Execution then jumps back to the recursion point, a loop or fn method.

;; Please see http://clojure.org/special_forms#recur
;; nil

(defn factorial-recur [n]
  (loop [count n acc 1]
    (if (zero? count)
      acc
      (recur (dec count) (* count acc)))))

(doc loop)
;; =>
;; -------------------------
;; clojure.core/loop
;; (loop [bindings*] exprs*)
;; Special Form
;; Evaluates the exprs in a lexical context in which the symbols in
;; the binding-forms are bound to their respective init-exprs or parts
;; therein. Acts as a recur target.

;; Please see http://clojure.org/special_forms#loop
;; nil

(declare is-even? is-odd?)

(doc declare)
;; =>
;; -------------------------
;; clojure.core/declare
;; ([& names])
;; Macro
;; defs the supplied var names with no bindings, useful for making forward declarations.
;; nil

(defn is-even? [n]
  (if (= n 0)
    true
    (is-odd? (dec n))))

(defn is-odd? [n]
  (if (= n 0)
    false
    (is-even? (dec n))))

(is-odd? 12)
;; => false
(is-even? 12)
;; => true

(declare is-even-2? is-odd-2?)

#(+ 1 2)
;; => #function[clojure-ntp.core/eval20359/fn--20360]

(defn is-even-2? [n]
  (if (= n 0)
    true
    #(is-odd-2? (dec n))))

(defn is-odd-2? [n]
  (if (= n 0)
    false
    #(is-even-2? (dec n))))

(trampoline is-odd-2? 12)
;; => false
(trampoline is-even-2? 12)
;; => true

(doc letfn)
;; =>
;; -------------------------
;; clojure.core/letfn
;; (letfn [fnspecs*] exprs*)
;; Special Form
;; fnspec ==> (fname [params*] exprs) or (fname ([params*] exprs)+)

;; Takes a vector of function specs and a body, and generates a set of
;; bindings of functions to their names. All of the names are available
;; in all of the definitions of the functions, as well as the body.
;; nil

(defn my-odd? [n]
  (letfn [(o? [n]
          (if (= n 0)
            false
            #(e? (dec n))))
          (e? [n]
            (if (= n 0)
              true
              #(o? (dec n))))]
    (trampoline o? n)))

(defn my-even? [n]
  (not (my-odd? n)))

(my-odd? 12)
;; => false
(my-even? 12)
;;=> true

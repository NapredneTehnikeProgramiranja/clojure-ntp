(ns clojure-ntp.macros)

(defmacro hebrew-lisp [form]
  (reverse form))

(hebrew-lisp ((str "Hello " p) [p] foo defn))

(foo "people!")
;; => Hello people!

(hebrew-lisp ("people!" foo))
;; => Hello people!

;; (((str "Hello ") [p] foo defn))
;; => CompilerException java.lang.RuntimeException: Unable to resolve symbol:
;;    p in this context, compiling:...

;; ("people!" foo)
;; => ClassCastException java.lang.String cannot be cast to clojure.lang.IFn
;;    clojure-ntp.macros/eval20385 (form-init7854233134659944327.clj:14)

(-> "(defn foo [p] (println (str p \" from some function foo.\")))"
    read-string
    eval)
;; => #'clojure-ntp.macros/foo

(-> "(foo \"Some text\")"
    read-string
    eval)
;; => Some text printed from some function foo.

(eval '(defn foo
         [p]
         (println (str p " printed from some function foo."))))
;; => #'clojure-ntp.macros/foo

(eval '(foo "Some text"))
;; => Some text printed from some function foo.

(defn foo
  [p]
  (println (str p " printed from some function foo.")))
;; => #'clojure-ntp.macros/foo

(foo "Some text")
;; => Some text printed from some function foo.

(list? (read-string "(+ 1 2)"))
;; => true

;; -----------------------------------------------------------------------------
;; Some macros

(read-string "#(println %)")
;; => (fn* [p1__20475#] (println p1__20475#))

(read-string "'(1 2 3)")
;; => (quote (1 2 3))

(read-string "@a")
;; => (clojure.core/deref a)

(read-string "; this is the comment\n(+ 1 2)")
;; => (+ 1 2)

;; -----------------------------------------------------------------------------
;; Macros detailed

(defmacro infix [form]
  (list
   (second form)
   (first form)
   (last form)))

(infix (1 + 2))
;; => 3

(read-string "(defmacro hebrew-lisp [form] (reverse form))")
;; => (defmacro hebrew-lisp [form] (reverse form))

(eval (read-string "(defmacro hebrew-lisp [form] (reverse form))"))
;; => #'clojure-ntp.macros/hebrew-lisp

(macroexpand (read-string "(hebrew-lisp ((str \"Hello \" p) [p] foo defn))"))
;; => (def foo (clojure.core/fn ([p] (str "Hello " p))))

(-> "(hebrew-lisp ((str \"Hello \" p) [p] foo defn))"
    read-string
    macroexpand
    eval)

(foo "good people")
;; => Hello good people

;; -----------------------------------------------------------------------------
;; Syntax quoting

`(def a1 (+ 1 2))
;; => (def clojure-ntp.macros/a1 (clojure.core/+ 1 2))

`(def a2 (* 43 ~(+ 1 2)))
;; => (def clojure-ntp.macros/a2 (clojure.core/* 43 3))

`~(def a3 (* 43 (+ 1 2)))
;; => #'clojure-ntp.macros/a3

a3
;; => 129

;; a1
;; => Error: Unable to resolve symbol: a1 in this context

;; a2
;; => Error: Unable to resolve symbol: a2 in this context

(let [forms `(+ [1 2 3])]
  `(def b (apply ~@forms)))
;; => (def clojure-ntp.macros/b (clojure.core/apply clojure.core/+ [1 2 3]))

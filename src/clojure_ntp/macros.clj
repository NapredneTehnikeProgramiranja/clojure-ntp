(ns clojure-ntp.macros)

(defmacro hebrew-lisp [form]
  (reverse form))

(hebrew-lisp ((str "Hello " p) [p] foo defn))

(foo "people!")n
;; => Hello people!

(hebrew-lisp ("people!" foo))
;; => Hello people!

(((str "Hello " p) [p] foo defn))
;; => CompilerException java.lang.RuntimeException: Unable to resolve symbol:
;;    p in this context, compiling:...

("people!" foo)
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

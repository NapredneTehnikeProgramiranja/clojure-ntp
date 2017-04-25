(ns clojure-ntp.atoms
  (:require [clojure.repl :refer [doc]]))

(def app-state (atom {}))
app-state
;; => #atom[{} 0x18a5b107]

(doc swap!)
;; =>
;; -------------------------
;; clojure.core/swap!
;; ([atom f] [atom f x] [atom f x y] [atom f x y & args])
;; Atomically swaps the value of atom to be:
;; (apply f current-value-of-atom args). Note that f may be called
;; multiple times, and thus should be free of side effects.  Returns
;; the value that was swapped in.
;; nil

(swap! app-state assoc :current-user "Mile")
;; => #atom[{:current-user "Mile"} 0x18a5b107]

(swap! app-state assoc :session-id "873649203")
;; => #atom[{:current-user "Mile", :session-id "873649203"} 0x18a5b107]

(reset! app-state {})
;; => #atom[{} 0x18a5b107]

(swap! app-state assoc :current-user "Pera" :session-id "98374374")
@app-state
;; => {:current-user "Pera", :session-id "98374374"}
(:current-user @app-state)
;; => "Pera"
(:session-id @app-state)
;; => 98374374
(:foo @app-state)
;; => nil

(doc future)
;; =>
;; -------------------------
;; clojure.core/future
;; ([& body])
;; Macro
;; Takes a body of expressions and yields a future object that will
;; invoke the body in another thread, and will cache the result and
;; return it on all subsequent calls to deref/@. If the computation has
;; not yet finished, calls to deref/@ will block, unless the variant of
;; deref with timeout is used. See also - realized?.
;; nil

(doc update-in)
;; =>
;; -------------------------
;; clojure.core/update-in
;; ([m [k & ks] f & args])
;; 'Updates' a value in a nested associative structure, where ks is a
;; sequence of keys and f is a function that will take the old value
;; and any supplied args and return the new value, and returns a new
;; nested structure.  If any levels do not exist, hash-maps will be
;; created.
;; nil

(def state (atom {:account 200}))

(defn multit []
  (future (swap! state update-in [:account]
                 (do
                   (println "Negotiations start...")
                   (Thread/sleep 10000)
                   (println "They're ready to pay!")
                   ;; They decided to support our budget by 50%
                   #(+ % (* % 0.5)))))
  (future (swap! state update-in [:account] (do
                                              (Thread/sleep 800)
                                              (println "We have to pay taxes.")
                                              #(- % 180)))))

;; @state
;; => {:account 200}
;; (multit)
;; Negotiations start...
;; #future[{:status :pending, :val nil} 0x499ef740]
;; We have to pay taxes.
;; @state
;; {:account 20}
;; They're ready to pay!
;; @state
;; {:account 30.0}

(defn multit-2 []
  (future (swap! state update-in [:account]
                 (do
                   (println "Negotiations start...")
                   (Thread/sleep 10000)
                   (println "They're ready to pay!")
                   ;; They decided to support our budget by 50%
                   #(+ % (* % 0.5)))))
  (future (swap! state update-in [:account] (do
                                              (println "We negotiate about taxes...")
                                              (Thread/sleep 15000)
                                              (println "We have to pay taxes.")
                                              #(- % 180)))))

;; @state
;; {:account 200}
;; (multit-2)
;; Negotiations start...
;; We negotiate about taxes...
;; #future[{:status :pending, :val nil} 0x1ec207bd]
;; They're ready to pay!
;; @state
;; {:account 300.0}
;; We have to pay taxes.
;; @state
;; {:account 120.0}

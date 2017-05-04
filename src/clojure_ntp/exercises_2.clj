(ns clojure-ntp.exercises-2)

(defmacro дефиниши-ф [name params form]
  `(defn ~name ~(vec params) ~form))

(дефиниши-ф здраво
  []
  (println "Здраво свима!"))

(здраво)
;; => Здраво свима!

(дефиниши-ф аритметичка-средина
  [бројеви]
  (/ (apply + бројеви) (-> бројеви
                           count
                           double)))

(аритметичка-средина (range 10))
;; => 4.5

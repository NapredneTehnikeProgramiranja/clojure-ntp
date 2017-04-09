(ns clojure-ntp.higher-order-functions
  (:require [clojure.repl :refer :all]))

(def students [{:city "Novi Sad" :name "Milica"}
               {:city "Loznica" :name "Jelena"}
               {:city "Loznica" :name "Milan"}
               {:city "Loznica" :name "Stefan"}
               {:city "Novi Sad" :name "Ivana"}])

(filter #(= "Loznica" (:city %)) students)
;; =>
;; ({:city "Loznica", :name "Jelena"}
;;  {:city "Loznica", :name "Milan"}
;;  {:city "Loznica", :name "Stefan"})

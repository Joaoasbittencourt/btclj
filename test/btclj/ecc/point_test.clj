(ns btclj.ecc.point-test
  (:require [btclj.ecc.point :as point]
            [clojure.test :refer [testing is deftest]]))

(def a [##Inf ##Inf])
(def b [2 5])
(def c [2 -5])

(deftest point-test
  (testing "add"
    (is (= b (point/add a b)))
    (is (= b (point/add b a)))
    (is (= a (point/add b c)))))

(ns btclj.ecc.finite-field-test
  (:require [btclj.ecc.finite-field :as ff]
            [clojure.test :refer [deftest is testing]]))

(deftest finite-element-test
  (testing "add"
    (is
     (= [17 31] (ff/add [2 31] [15 31])))
    (is
     (= [7 31] (ff/add [17 31] [21 31]))))

  (testing "sub"
    (is
     (= [25 31] (ff/sub [29 31] [4 31])))
    (is
     (= [16 31] (ff/sub [15 31] [30 31]))))

  (testing "mul"
    (is
     (= [22 31] (ff/mul [24 31] [19 31]))))

  (testing "pow"
    (is
     (= [15 31] (ff/pow [17 31] 3)))
    (is
     (= [29 31] (ff/pow [17 31] -3)))
    (is
     (= [16 31] (ff/mul (ff/pow [5 31] 5) [18 31]))))

  (testing "div"
    (is
     (= [4 31] (ff/div [3 31] [24 31])))))



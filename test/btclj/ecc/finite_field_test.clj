(ns btclj.ecc.finite-field-test
  (:require [btclj.ecc.finite-field :as ff]
            [clojure.test :refer [deftest is testing]]))

(deftest finite-element-test
  (testing "add"
    (is (= 17 (ff/add 31 2 15)))
    (is (= 7 (ff/add 31 17 21))))

  (testing "sub"
    (is (= 25 (ff/sub 31 29 4)))
    (is (= 16 (ff/sub 31 15 30))))

  (testing "mul"
    (is (= 22 (ff/mul 31 24 19))))

  (testing "pow"
    (is (= 15 (ff/pow 31 17 3)))
    (is (= 29 (ff/pow 31 17 -3)))
    (is (= 16 (ff/mul 31 (ff/pow 31 5 5) 18))))

  (testing "div"
    (is (= 4 (let [div-on-f31 (ff/div 31)]
               (div-on-f31 3 24))))))

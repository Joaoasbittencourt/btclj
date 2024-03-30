(ns btclj.serialization.der-test
  (:require [btclj.serialization.der :refer [der->sig sig->der]]
            [clojure.test :refer [deftest is testing]]))

(def test-sig-der "3045022037206a0610995c58074999cb9767b87af4c4978db68c06e8e6e81d282047a7c60221008ca63759c1157ebeaec0d03cecca119fc9a75bf8e6d0fa65c841c8e2738cdaec")

(def test-sig
  [0x37206a0610995c58074999cb9767b87af4c4978db68c06e8e6e81d282047a7c6
   0x8ca63759c1157ebeaec0d03cecca119fc9a75bf8e6d0fa65c841c8e2738cdaec])

(deftest der-test
  (testing "can encode a signature in der format"
    (is (= test-sig-der (sig->der test-sig))))
  (testing "can decode a signature from der format"
    (is (= test-sig (der->sig test-sig-der)))))

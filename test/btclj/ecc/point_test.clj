(ns btclj.ecc.point-test
  (:require [btclj.ecc.point :as point]
            [clojure.test :refer [testing is deftest]]))

(def params [223 0 7])

(deftest point-test
  (testing "add"
    (is (= [49 71] (point/add params [192 105] [192 105])))
    (is (= [64 168] (point/add params [143 98] [143 98])))
    (is (= [36 111] (point/add params [47 71] [47 71])))
    (is (= [194 51] (reduce
                     (fn [acc next] (point/add params acc next))
                     (repeat 4 [47 71]))))

    (is (= [116 55] (reduce
                     (fn [acc next] (point/add params acc next))
                     (repeat 8 [47 71]))))

    (is (= [##Inf ##Inf] (reduce
                          (fn [acc next] (point/add params acc next))
                          (repeat 21 [47 71])))))

  (testing "smul"
    (is (= [49 71] (point/smul params [192 105] 2)))
    (is (= [64 168] (point/smul params [143 98] 2)))
    (is (= [36 111] (point/smul params [47 71] 2)))
    (is (= [194 51] (point/smul params [47 71] 4)))
    (is (= [116 55] (point/smul params [47 71] 8)))
    (is (= [##Inf ##Inf] (point/smul params [47 71] 21))))

  (testing "in?"
    (is (= (seq [[192 105] [17 56] [1 193]])
           (let [params [223 0 7]
                 points [[192 105]
                         [17 56]
                         [200 119]
                         [1 193]
                         [42 99]]]
             (filter
              #(point/in? params %) points)))))

  (testing "if is able to find group order"
    (is (= 7 (let [x 15
                   y 86
                   params [223 0 7]]
               (loop [point [x y]
                      count 1]
                 (if (= [##Inf ##Inf] point) count
                     (recur (point/add params point [x y])
                            (inc count)))))))))

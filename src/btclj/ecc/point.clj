(ns btclj.ecc.point
  (:require [btclj.ecc.finite-field :as ff]
            [btclj.numeric.big-ints :refer [big-and]]))

(defn in? [[p a b] [x y]]
  (let [pow (ff/pow p)
        add (ff/add p)
        mul (ff/mul p)]
    (= (pow y 2)
       (add
        (add (pow x 3)
             (mul a x)) b))))

(defn -add-same-point [[p a] [x y]]
  (let [pow (ff/pow p)
        add (ff/add p)
        mul (ff/mul p)
        smul (ff/smul p)
        sub (ff/sub p)
        div (ff/div p)

        s (div (add (smul
                     (pow x 2) 3) a)
               (smul y 2))
        x3 (sub (pow s 2)
                (smul x 2))
        y3 (sub (mul s (sub x x3))
                y)]
    [x3 y3]))

(defn -add-different-points [[p] p1 p2]
  (let [pow (ff/pow p)
        mul (ff/mul p)
        sub (ff/sub p)
        div (ff/div p)
        [x1 y1] p1
        [x2 y2] p2
        s (div (sub y2 y1)
               (sub x2 x1))
        x3 (sub (sub (pow s 2) x1) x2)
        y3 (sub (mul s (sub x1 x3))
                y1)]
    [x3 y3]))

(defn add [[p a] p1 p2]
  (let [[x1 y1] p1
        [x2 y2] p2]
    (cond
      (infinite? x1) p2
      (infinite? x2) p1

      (and (= x1 x2)
           (not= y1 y2)) [##Inf ##Inf]

      (= p1 p2) (-add-same-point [p a] p1)

      :else (-add-different-points [p] p1 p2))))

(defn smul [[p a] point scalar]
  (let [add-point (fn [p1 p2] (add [p a] p1 p2))]
    (loop [coef (biginteger scalar)
           current point
           result [##Inf ##Inf]]
      (if (zero? coef) result
          (recur (.shiftRight coef 1)
                 (add-point current current)
                 (if (= 1 (big-and coef 1)) (add-point result current)
                     result))))))

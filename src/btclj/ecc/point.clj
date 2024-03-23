(ns btclj.ecc.point)

(defn -scalar-big-pow [num exp]
  (biginteger
   (.pow
    (biginteger num)
    (biginteger exp))))

(defn in? [[x y]  [a b]]
  (= (-scalar-big-pow y 2)
     (-> (-scalar-big-pow x 3)
         (+ (* a x))
         (+ b))))

(defn add [p1 p2]
  (let [[x1 y1] p1
        [x2 y2] p2]
    (cond
      (= ##Inf x1) p2
      (= ##Inf x2) p1
      (and (= x1 x2)
           (not= y1 y2)) [##Inf ##Inf]
      :else (let [s (/ (- y2 y1)
                       (- x2 x1))
                  x3 (- (-scalar-big-pow s 2) x1 x2)
                  y3 (- (* s (- x1 x3))
                        y1)]
              [x3 y3]))))

(comment
  (add  [2 5] [-1 -1]))

(comment
  ;; points inside a curve
  (let [curve [5 7]
        points [[2 4]
                [-1 -1]
                [18 77]
                [5 7]]]
    (filter
     #(in? % curve) points)))


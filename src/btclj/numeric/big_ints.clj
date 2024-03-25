(ns btclj.numeric.big-ints)

(defn big-and
  [f & r]
  (reduce (fn [acc v] (.and acc (biginteger v))) (biginteger f) r))

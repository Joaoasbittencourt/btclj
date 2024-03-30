(ns btclj.utils.big-ints)

(defn big-and
  [f & r]
  (reduce (fn [acc v] (.and acc (biginteger v))) (biginteger f) r))

(defn mod-pow' [base exp n]
  (.modPow
   (biginteger base)
   (biginteger exp)
   (biginteger n)))

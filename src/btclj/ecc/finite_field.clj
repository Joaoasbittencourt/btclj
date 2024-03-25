(ns btclj.ecc.finite-field)

(defn add
  ([p n1 n2] (mod (+ n1 n2) p))
  ([p n1] (fn [n2] (add p n1 n2)))
  ([p] (fn [n1 n2] (add p n1 n2))))

(defn sub
  ([p n1 n2] (mod (- n1 n2) p))
  ([p n1] (fn [n2] (sub p n1 n2)))
  ([p] (fn [n1 n2] (sub p n1 n2))))

(defn mul
  ([p n1 n2] (mod (* n1 n2) p))
  ([p n1] (fn [n2] (mul p n1 n2)))
  ([p] (fn [n1 n2] (mul p n1 n2))))

(defn pow
  ([p n exp]
   (if (zero? exp) 1
       (.modPow
        (biginteger n)
        (biginteger (.mod (biginteger exp)
                          (biginteger (dec p))))
        (biginteger p))))
  ([p] (fn [n exp] (pow p n exp)))
  ([p n] (fn [exp] (pow p n exp))))

(defn div
  ([p n1 n2]
   (let [n1 (biginteger n1)
         n2 (biginteger n2)
         p (biginteger p)
         p-2 (biginteger (- p 2))]
     (mod
      (* n1 (.modPow n2 p-2 p)) p)))
  ([p n1] (fn [n2] (div p n1 n2)))
  ([p] (fn [n1 n2] (div p n1 n2))))

(defn smul
  ([prime num scalar]
   (mod (* num scalar) prime))
  ([prime]
   (fn [num scalar]
     (smul prime num scalar))))



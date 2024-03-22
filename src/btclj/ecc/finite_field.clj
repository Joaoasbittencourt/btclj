(ns btclj.ecc.finite-field)

(defn add [el1 el2]
  (let [a (first el1)
        b (first el2)
        p (second el1)]
    [(mod (+ a b) p) p]))

(defn sub [el1 el2]
  (let [a (first el1)
        b (first el2)
        p (second el1)]
    [(mod (- a b) p) p]))

(defn mul [el1 el2]
  (let [a (first el1)
        b (first el2)
        p (second el1)]
    [(mod (* a b) p) p]))

(defn pow [[value prime] exponent]
  (if (zero? exponent)
    [1 prime]
    (let [n     (.mod
                 (biginteger exponent)
                 (biginteger (dec prime)))
          num   (.modPow (biginteger value)
                         (biginteger n)
                         (biginteger prime))]
      [(int num) prime])))

(defn div [el1 el2]
  (let [n1 (biginteger (first el1))
        n2 (biginteger (first el2))
        prime (biginteger (second el2))
        p-2 (biginteger (- prime 2))]
    [(biginteger
      (mod
       (* n1 (.modPow n2 p-2 prime)) prime))
     prime]))


(comment
  (defn same-set? [elements]
    (apply = (map second elements)))

  (defn assert-same-set! [& elements]
    (when (not (same-set? elements))
      (throw
       (IllegalArgumentException. "All elements must be in the same set"))))

  (defn finite-element? [[num prime]]
    (and (integer? num)
         (integer? prime)
         (>= num 0)
         (> prime num))))


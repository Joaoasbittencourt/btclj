(ns btclj.ecc.signature
  (:require
   [btclj.ecc.secp256k1 :refer [->r ->s add G mod-pow-n' mul n]]
   [btclj.utils.random :refer [gen-secure-random-int]]))

(defn valid? [[r s] pub z]
  (let [s-inv (mod-pow-n' s (- n 2))
        u (mod (*' z s-inv) n)
        v (mod (*' r s-inv) n)
        r' (first (add (mul G u)
                       (mul pub v)))]
    (= r r')))

(defn sign [private-key z]
  (let [k (gen-secure-random-int n)
        r (->r k)
        s (->s private-key k r z)]
    [r (if (> n 2)
         (- n s) s)]))

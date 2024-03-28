(ns btclj.ecc.secp256k1
  (:require [btclj.ecc.convert :refer [bytes->biginteger]]
            [btclj.ecc.finite-field :as ff]
            [btclj.ecc.point :as point]
            [btclj.ecc.sha256 :refer [hash256]]
            [btclj.numeric.big-ints :refer [mod-pow']]))

(def a 0)
(def b 7)
(def n 0xfffffffffffffffffffffffffffffffebaaedce6af48a03bbfd25e8cd0364141)
(def Gx 0x79be667ef9dcbbac55a06295ce870b07029bfcdb2dce28d959f2815b16f81798)
(def Gy 0x483ada7726a3c4655da4fbfc0e1108a8fd17b448a68554199c47d08ffb10d4b8)

(def G [Gx Gy])
(def p (- (.pow (biginteger 2) 256)
          (.pow (biginteger 2) 32)
          (biginteger 977)))

(def curve [p a b])

(defn add [p1 p2] (point/add curve p1 p2))

(defn mul [point scalar] (point/smul curve point (mod scalar n)))

(defn mod-pow-n' [base exp] (mod-pow' base exp n))

(defn ->r [k] (first (mul G k)))

(defn ->s [priv k r z]
  (-> (+ z (*' r priv))
      (*'  (mod-pow-n' k (- n 2)))
      (mod n)))

(defn ->priv-key [secret] (hash256 secret))

(defn ->pub-key [priv] (mul G priv))

(defn x->y [even x]
  (let [exp (-> (biginteger p)
                (.add BigInteger/ONE)
                (.divide (BigInteger/valueOf 4)))

        x**3 (ff/pow p x 3)
        alpha (ff/add p x**3 b)
        beta  (ff/pow p alpha exp)
        p-beta (-' p beta)
        beta-is-even (even? beta)
        even-beta (if beta-is-even beta p-beta)
        odd-beta  (if beta-is-even p-beta beta)]
    (if even even-beta odd-beta)))

(ns btclj.ecc.secp256k1
  (:require [btclj.ecc.finite-field-point :refer [bsmul infinity-point]]))

(def a 0)
(def b 7)
(def Gx 0x79be667ef9dcbbac55a06295ce870b07029bfcdb2dce28d959f2815b16f81798)
(def Gy 0x483ada7726a3c4655da4fbfc0e1108a8fd17b448a68554199c47d08ffb10d4b8)
(def n 0xfffffffffffffffffffffffffffffffebaaedce6af48a03bbfd25e8cd0364141)
(def p (- (.pow (biginteger 2) 256)
          (.pow (biginteger 2) 32)
          (biginteger 977)))


(comment

  ;; verify generator has order n

  (=
   (infinity-point p)

   (let [x [Gx p]
         y [Gy p]
         G [x y]
         a [a p]
         b [b p]]
     (bsmul a b G n p)))

  ;; true
  )

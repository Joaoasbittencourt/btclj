(ns btclj.ecc.serialization
  (:require [btclj.ecc.convert :refer [hex->biginteger]]
            [btclj.ecc.secp256k1 :as secp256k1]))

(defn- hex-32 [n]
  (format
   (str "%0" (* 2 32) "x")
   (biginteger n)))

(defn- usec->point [sec]
  [(hex->biginteger (subs sec 2 66))
   (hex->biginteger (subs sec 66 130))])

(defn- csec->x [sec]
  (hex->biginteger (subs sec 2)))

(defn point->usec [[x y]]
  (str
   "04" (hex-32 x) (hex-32 y)))

(defn point->csec [[x y]]
  (str
   (if (odd? y) "03" "02") (hex-32 x)))

(defn sec->point [sec]
  (let [marker (subs sec 0 2)]
    (if (= "04" marker)
      (usec->point sec)
      (let [even (=  marker "02")
            x (csec->x sec)]
        [x (secp256k1/x->y even x)]))))

(ns btclj.ecc.sha256
  (:require [btclj.ecc.convert :refer [bytes->biginteger]]))

(defn sha256 [bytes]
  (.digest
   (java.security.MessageDigest/getInstance "SHA-256")
   bytes))

(defn hash256 [data]
  (->
   (cond
     (string? data) (.getBytes data)
     (instance? BigInteger data) (.toByteArray data)
     (int? data) (.toByteArray (biginteger data))
     (bytes? data) data
     :else (throw (IllegalArgumentException. "Unsupported data type")))
   sha256
   sha256
   bytes->biginteger))

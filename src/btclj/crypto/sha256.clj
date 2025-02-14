(ns btclj.crypto.sha256
  (:require [btclj.utils.convert :refer [bytes->biginteger]]))

(defn sha256 [bytes]
  (.digest
   (java.security.MessageDigest/getInstance "SHA-256")
   bytes))

(defn bhash256 [data]
  (->
   (cond
     (string? data) (.getBytes data)
     (instance? BigInteger data) (.toByteArray data)
     (int? data) (.toByteArray (biginteger data))
     (bytes? data) data
     :else (throw (IllegalArgumentException. "Unsupported data type")))
   (sha256)
   (sha256)))

(defn hash256 [data]
  (bytes->biginteger (bhash256 data)))

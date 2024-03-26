(ns btclj.ecc.sha256
  (:require [btclj.ecc.convert :refer [bytes->biginteger]]))

(defn sha256 [bytes]
  (.digest
   (java.security.MessageDigest/getInstance "SHA-256")
   bytes))

(defn hash256 [str]
  (-> str .getBytes sha256 sha256 bytes->biginteger))

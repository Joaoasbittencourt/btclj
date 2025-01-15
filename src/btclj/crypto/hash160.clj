(ns btclj.crypto.hash160
  (:require [btclj.crypto.ripemd160 :refer [ripemd160]]
            [btclj.crypto.sha256 :refer [sha256]]))

(defn hash160
  "sha256 followed by ripemd160"
  [bytes]
  (-> bytes sha256 ripemd160))

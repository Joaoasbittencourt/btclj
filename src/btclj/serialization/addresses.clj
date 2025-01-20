(ns btclj.serialization.addresses
  (:require
   [btclj.crypto.hash160 :refer [hash160]]
   [btclj.serialization.base58 :refer [encode-checksum]]
   [btclj.utils.convert :as cvt]))

(def network-prefixes
  {:testnet "6f"
   :mainnet "00"})

(defn append-net-prefix
  "Append the network prefix to a hash"
  [b net]
  (-> b
      (cvt/hexify)
      (->> (str (network-prefixes net)))
      (cvt/hex->bytes)))

(defn address [sec-pub network]
  (-> sec-pub
      (cvt/hex->bytes)
      (hash160)
      (append-net-prefix network)
      (encode-checksum)))

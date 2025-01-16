(ns btclj.serialization.wif
  (:require
   [btclj.serialization.base58 :as base58]
   [btclj.utils.convert :as cvt]
   [btclj.utils.hex :refer [hex-32]]))

(defn wif
  ([secret compressed network]
   (let [prefix (if (= network :testnet) "ef" "80")
         suffix (if compressed "01" "")
         combined (str prefix (hex-32 secret) suffix)]
     (base58/encode-checksum (cvt/hex->bytes combined))))
  ([secret compressed]
   (wif secret compressed :mainnet))
  ([secret]
   (wif secret true :mainnet)))

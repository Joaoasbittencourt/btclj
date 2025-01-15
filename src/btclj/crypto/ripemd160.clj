(ns btclj.crypto.ripemd160
  (:import
   [java.security Security MessageDigest]
   [org.bouncycastle.jce.provider BouncyCastleProvider]))

(defn- register-bouncy-castle []
  (when (nil? (Security/getProvider "BC"))
    (Security/addProvider (BouncyCastleProvider.))))

(defn ripemd160
  [bytes]
  (register-bouncy-castle)
  (let [digest (MessageDigest/getInstance "RIPEMD160" "BC")]
    (.digest digest bytes)))

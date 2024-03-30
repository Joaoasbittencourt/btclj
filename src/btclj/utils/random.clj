(ns btclj.utils.random)

(defn gen-secure-random-int [range]
  (let [random (java.security.SecureRandom/getInstance "SHA1PRNG")
        bitLength (.bitLength range)
        byteLength (int (Math/ceil (/ bitLength 8.0)))
        randomBytes (byte-array byteLength)]
    (.nextBytes random randomBytes)
    (let [randomBigInt (java.math.BigInteger. 1 randomBytes)]
      (mod randomBigInt range))))

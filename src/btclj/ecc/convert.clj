(ns btclj.ecc.convert)

(defn bytes->hex [n]
  (format "%x" (biginteger n)))

(defn biginteger->hex [n]
  (-> n
      biginteger
      .toByteArray
      bytes->hex))

(defn bytes->biginteger [bytes]
  (BigInteger. 1 bytes))

(defn hex->biginteger [hex]
  (BigInteger. hex 16))




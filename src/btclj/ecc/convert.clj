(ns btclj.ecc.convert)

(defn bytes->hex [bytes]
  (apply str (map (partial format "%02x") bytes)))

(defn bytes->biginteger [bytes]
  (BigInteger. (bytes->hex bytes) 16))

(defn biginteger->hex [n]
  (.toString (biginteger n) 16))

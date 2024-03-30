(ns btclj.utils.convert)

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

(defn biginteger->bytes [num]
  (.toByteArray num))

(defn hex->bytes [hex]
  (-> hex
      hex->biginteger
      biginteger->bytes))

(defn edian->bytes [big-int byte-length byte-order]
  (let [bytes (.toByteArray big-int)
        padding-size (max 0 (- byte-length (count bytes)))
        padded-bytes (concat (repeat padding-size 0) (seq bytes))]
    (byte-array
     (if (= byte-order :big)
       padded-bytes
       (reverse padded-bytes)))))

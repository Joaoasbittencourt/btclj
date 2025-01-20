(ns btclj.utils.convert
  (:require
   [btclj.utils.convert :as cvt]))

(defn hexify [bytes]
  (apply str
         (map #(format "%02x" %) bytes)))

;; check why changing this to hexify approach we break the tests
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

(defn hex->bytes
  "Convert hex string to byte sequence"
  [s]
  (letfn [(unhexify-2 [c1 c2]
            (unchecked-byte
             (+ (bit-shift-left (Character/digit c1 16) 4)
                (Character/digit c2 16))))]
    (->> (partition 2 s)
         (map #(apply unhexify-2 %))
         (byte-array))))

(defn edian->bytes [big-int byte-length byte-order]
  (let [bytes (.toByteArray big-int)
        padding-size (max 0 (- byte-length (count bytes)))
        padded-bytes (concat (repeat padding-size 0) (seq bytes))]
    (byte-array
     (if (= byte-order :big)
       padded-bytes
       (reverse padded-bytes)))))

(defn little-edian->bigint [bytes]
  (BigInteger. 1 (byte-array (reverse bytes))))

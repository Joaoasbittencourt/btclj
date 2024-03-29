(ns btclj.ecc.serialization.der
  (:require [btclj.ecc.convert :as cvt]))

(defn- remove-leading-zeros [bytes]
  (let [first-non-zero
        (some (fn [i] (if (not= (get bytes i) 0) i false))
              (range (count bytes)))]
    (if first-non-zero
      (drop first-non-zero bytes)
      (byte-array [0]))))

(defn- ajust-highest-bit [arr]
  (if
   (not= 0 (bit-and (first arr) 0x80))
    (conj (seq arr) 0x00) arr))

(defn- mark-bytes [xs tag]
  (conj xs (count xs) tag))

(defn sig->der [[r s]]
  (let [encode #(-> (biginteger %)
                    (cvt/edian->bytes 32 :big)
                    (remove-leading-zeros)
                    (ajust-highest-bit)
                    (seq))]
    (-> (concat (mark-bytes (encode r) 0x02)
                (mark-bytes (encode s) 0x02))
        (mark-bytes 0x30)
        (byte-array)
        (cvt/bytes->hex))))

(defn der->sig [hex]
  (->> hex
       (cvt/hex->bytes)
       (partition-by (fn [x] (not= x 2)))
       (drop 1)
       (filter #(not= (seq [2]) %))
       (map (comp
             cvt/bytes->biginteger
             byte-array
             rest))
       (vec)))

(ns btclj.serialization.base58
  (:require
   [btclj.crypto.sha256 :refer [sha256]]
   [btclj.utils.convert :as cvt]))

(def alphabet "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz")
(def alphabet-count (count alphabet))

(defn- leading-zeros
  "Count the number of leading zero bytes in a byte array."
  [bytes]
  (->> bytes
       (take-while zero?) count))

(defn encode
  "Encode a byte array into a base58 string."
  [bytes]
  (loop [num (BigInteger. 1 (byte-array bytes))
         result ""]
    (if (zero? num)
      (str
       (apply str (repeat (leading-zeros bytes) \1)) result)
      (let [rem  (mod num alphabet-count)
            char (nth alphabet rem)]
        (recur
         (quot num alphabet-count)
         (str char result))))))

(defn decode
  "Decode a base58 string into a byte array."
  [string]
  (let [num (reduce (fn [n c]
                      (->
                       n
                       (*' alphabet-count)
                       (+' (.indexOf alphabet (str c)))))
                    (biginteger 0)
                    string)
        combined (cvt/edian->bytes
                  (biginteger num) 25 :big)]
    (byte-array combined)))

(defn checksum
  "Calculate the checksum of a byte array."
  [bytes]
  (->> bytes
       (sha256)
       (sha256)
       (take 4)
       (byte-array)))

(defn encode-checksum
  "Encode a byte array into a base58 string with a checksum."
  [bytes]
  (-> bytes
      (concat (checksum bytes))
      (byte-array)
      (encode)))

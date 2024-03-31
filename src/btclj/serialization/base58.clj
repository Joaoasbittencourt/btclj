(ns btclj.serialization.base58
  (:require [btclj.utils.convert :as cvt]))

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

(-> "eff69ef2b1bd93a66ed5219add4fb51e11a840f404876325a1e8ffe0529a2c"
    (cvt/hex->bytes)
    (encode)
    (decode)
    (cvt/bytes->hex)
    (= "eff69ef2b1bd93a66ed5219add4fb51e11a840f404876325a1e8ffe0529a2c")


    ;
    )




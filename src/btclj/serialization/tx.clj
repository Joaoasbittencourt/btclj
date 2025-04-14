(ns btclj.serialization.tx
  (:require
   [btclj.utils.convert :refer [hex->bytes little-edian->bigint]]))

(defn varint [^bytes b offset]
  (let [prefix (bit-and (aget b offset) 0xFF)]
    (cond
      (< prefix 0xfd) [prefix 1]
      (= prefix 0xfd) [(little-edian->bigint (subvec (vec b) (inc offset) (+ offset 3))) 3]
      (= prefix 0xfe) [(little-edian->bigint (subvec (vec b) (inc offset) (+ offset 5))) 5]
      :else           [(little-edian->bigint (subvec (vec b) (inc offset) (+ offset 9))) 9])))

(defn bytes->hex [bytes]
  (apply str (map #(format "%02x" (bit-and % 0xFF)) bytes)))

(defn read-bytes [^bytes b offset len]
  [(subvec (vec b) offset (+ offset len))
   (+ offset len)])

(defn read-version [b]
  (let [[bytes offset] (read-bytes b 0 4)]
    [(little-edian->bigint bytes) offset]))

(defn read-collection [b offset count parse-fn]
  (reduce
   (fn [[acc off] _]
     (let [[item new-off] (parse-fn b off)]
       [(conj acc item) new-off]))
   [[] offset]
   (range count)))

(defn read-varint+offset [b offset]
  (let [[n len] (varint b offset)]
    [n (+ offset len)]))

(defn read-locktime [b offset]
  (let [[bytes _] (read-bytes b offset 4)]
    (little-edian->bigint bytes)))

(defn parse-input [b offset]
  (let [[txid-bytes tx-id-offset] (read-bytes b offset 32)
        txid (bytes->hex (byte-array (reverse txid-bytes)))

        [prev-idx-bytes prev-id-offset] (read-bytes b tx-id-offset 4)
        prev-idx (little-edian->bigint prev-idx-bytes)

        [script-len slen] (varint b prev-id-offset)
        script-offset (+ prev-id-offset slen)

        [script-bytes script-offset] (read-bytes b script-offset script-len)
        script (bytes->hex (byte-array script-bytes))

        [sequence-bytes sequence-offset] (read-bytes b script-offset 4)
        sequence (little-edian->bigint sequence-bytes)]
    [{:txid txid
      :prev-idx prev-idx
      :script-sig script
      :sequence sequence}
     sequence-offset]))

(defn parse-output [b offset]
  (let [[value-bytes offset] (read-bytes b offset 8)
        value (little-edian->bigint value-bytes)
        [script-len slen] (varint b offset)
        offset (+ offset slen)
        [script-bytes offset] (read-bytes b offset script-len)
        script (bytes->hex (byte-array script-bytes))]
    [{:value value
      :script-pkey script}
     offset]))

(defn parse-tx [hex]
  (let [b (hex->bytes hex)
        [version   offset1] (read-version b)
        [in-count  offset2] (read-varint+offset b offset1)
        [inputs    offset3] (read-collection b offset2 in-count parse-input)
        [out-count offset4] (read-varint+offset b offset3)
        [outputs   offset5] (read-collection b offset4 out-count parse-output)
        locktime (read-locktime b offset5)]
    {:version version
     :inputs inputs
     :outputs outputs
     :locktime locktime}))

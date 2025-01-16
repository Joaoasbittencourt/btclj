(ns btclj.utils.hex)

(defn hex-32 [n]
  (format
   (str "%0" (* 2 32) "x")
   (biginteger n)))

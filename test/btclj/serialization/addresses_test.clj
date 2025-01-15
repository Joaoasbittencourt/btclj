(ns btclj.serialization.addresses-test
  (:require
   [btclj.ecc.secp256k1 :refer [->pub-key]]
   [btclj.serialization.addresses :refer [address]]
   [btclj.serialization.sec :refer [point->csec point->usec]]
   [clojure.test :refer [deftest is testing]]))

(deftest base58-test
  (testing "compressed"
    (is
     (= "mopVkxp8UhXqRYbCYJsbeE1h1fiF64jcoH"
        (-> (->pub-key 33632321603200000)
            (point->csec)
            (address :testnet)))))

  (testing "uncompressed"
    (is
     (= "mmTPbXQFxboEtNRkwfh6K51jvdtHLxGeMA"
        (-> (->pub-key 5002)
            (point->usec)
            (address :testnet))))))


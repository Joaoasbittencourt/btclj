(ns btclj.ecc.signature-test
  (:require [btclj.ecc.secp256k1 :as secp256k1]
            [btclj.ecc.sha256 :refer [hash256]]
            [btclj.ecc.signature :as sig]
            [clojure.test :refer [deftest is testing]]))

(deftest signature-test
  (testing "sign and verify"
    (is (let [priv         (secp256k1/->priv-key "my secret")
              pub          (secp256k1/->pub-key priv)
              message-hash (hash256 "my message")
              signature    (sig/sign priv message-hash)]
          (sig/valid? signature pub message-hash)))))

(ns btclj.serialization.sec-test
  (:require [btclj.ecc.secp256k1 :as secp256k1]
            [btclj.serialization.sec :as ser]
            [clojure.test :refer [deftest is testing]]))

(deftest uncompressed-sec-test
  (testing "can find the uncompressed sec of a point"
    (is (=
         "04ffe558e388852f0120e46af2d1b370f85854a8eb0841811ece0e3e03d282d57c315dc72890a4f10a1481c031b03b351b0dc79901ca18a00cf009dbdb157a1d10"
         (-> 5000
             secp256k1/->pub-key
             ser/point->usec)))
    (is (=
         "04027f3da1918455e03c46f659266a1bb5204e959db7364d2f473bdf8f0a13cc9dff87647fd023c13b4a4994f17691895806e1b40b57f4fd22581a4f46851f3b06"
         (-> (biginteger 2018)
             (.pow 5)
             secp256k1/->pub-key
             ser/point->usec)))
    (is (=
         "04d90cd625ee87dd38656dd95cf79f65f60f7273b67d3096e68bd81e4f5342691f842efa762fd59961d0e99803c61edba8b3e3f7dc3a341836f97733aebf987121"
         (-> 0xdeadbeef12345
             secp256k1/->pub-key
             ser/point->usec)))
    (testing "can parse an ucompressed sec formated string"
      (is (let [point (-> 5000 secp256k1/->pub-key)
                serialized (ser/point->usec point)]
            (= point (ser/sec->point serialized))))

      (is (let [point (-> (biginteger 2019)
                          (.pow 5)
                          secp256k1/->pub-key)
                serialized (ser/point->usec point)]
            (= point (ser/sec->point serialized))))

      (is (let [point (-> 0xdeadbeef12345 secp256k1/->pub-key)
                serialized (ser/point->usec point)]
            (= point (ser/sec->point serialized)))))))

(deftest compressed-sec-test
  (testing "can find compressed sec of a point"
    (is (=
         "0357a4f368868a8a6d572991e484e664810ff14c05c0fa023275251151fe0e53d1"
         (-> 5001
             secp256k1/->pub-key
             ser/point->csec)))
    (is (=
         "02933ec2d2b111b92737ec12f1c5d20f3233a0ad21cd8b36d0bca7a0cfa5cb8701"
         (-> (biginteger 2019)
             (.pow 5)
             secp256k1/->pub-key
             ser/point->csec)))
    (is (=
         "0296be5b1292f6c856b3c5654e886fc13511462059089cdf9c479623bfcbe77690"
         (-> 0xdeadbeef54321
             secp256k1/->pub-key
             ser/point->csec))))

  (testing "can parse a compressed sec formated string"
    (is (let [point (-> 5001 secp256k1/->pub-key)
              serialized (ser/point->csec point)]
          (= point (ser/sec->point serialized))))

    (is (let [point (-> (biginteger 2019)
                        (.pow 5)
                        secp256k1/->pub-key)
              serialized (ser/point->csec point)]
          (= point (ser/sec->point serialized))))

    (is (let [point (-> 0xdeadbeef54321 secp256k1/->pub-key)
              serialized (ser/point->csec point)]
          (= point (ser/sec->point serialized))))))

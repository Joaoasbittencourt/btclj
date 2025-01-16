(ns btclj.serialization.wif-test
  (:require
   [btclj.serialization.wif :refer [wif]]
   [clojure.test :refer [deftest is testing]]))

(deftest wif-test
  (testing "can encode a small number"
    (is
     (= "cMahea7zqjxrtgAbB7LSGbcQUr1uX1ojuat9jZodMN8rFTv2sfUK"
        (wif 5003 true :testnet))))

  (testing "can encode uncompressed"
    (is
     (= "91avARGdfge8E4tZfYLoxeJ5sGBdNJQH4kvjpWAxgzczjbCwxic"
        (wif (.pow (biginteger 2021) 5) false :testnet))))

  (testing "can encode for the mainnet"
    (is
     (= "KwDiBf89QgGbjEhKnhXJuH7LrciVrZi3qYjgiuQJv1h8Ytr2S53a"
        (wif 0x54321deadbeef)))))





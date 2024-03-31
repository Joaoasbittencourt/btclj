(ns btclj.serialization.base58-test
  (:require [btclj.serialization.base58 :as base58]
            [btclj.utils.convert :as cvt]
            [clojure.test :refer [deftest is testing]]))

(deftest base58-test
  (testing "encoding"
    (is (= "4fE3H2E6XMp4SsxtwinF7w9a34ooUrwWe4WsW1458Pd"
           (-> "eff69ef2b1bd93a66ed5219add4fb51e11a840f404876325a1e8ffe0529a2c"
               (cvt/hex->bytes)
               (base58/encode))))

    (is (= "9MA8fRQrT4u8Zj8ZRd6MAiiyaxb2Y1CMpvVkHQu5hVM6"
           (-> "7c076ff316692a3d7eb3c3bb0f8b1488cf72e1afcd929e29307032997a838a3d"
               (cvt/hex->bytes)
               (base58/encode))))

    (is (= "EQJsjkd6JaGwxrjEhfeqPenqHwrBmPQZjJGNSCHBkcF7"
           (-> "c7207fee197d27c618aea621406f6bf5ef6fca38681d82b2f06fddbdce6feab6"
               (cvt/hex->bytes)
               (base58/encode)))))

  (testing "decoding"
    (is (= "7c076ff316692a3d7eb3c3bb0f8b1488cf72e1afcd929e29307032997a838a3d"
           (-> "9MA8fRQrT4u8Zj8ZRd6MAiiyaxb2Y1CMpvVkHQu5hVM6"
               (base58/decode)
               (cvt/bytes->hex))))
    (is (= "eff69ef2b1bd93a66ed5219add4fb51e11a840f404876325a1e8ffe0529a2c"
           (-> "4fE3H2E6XMp4SsxtwinF7w9a34ooUrwWe4WsW1458Pd"
               (base58/decode)
               (cvt/bytes->hex))))
    (is (= "c7207fee197d27c618aea621406f6bf5ef6fca38681d82b2f06fddbdce6feab6"
           (-> "EQJsjkd6JaGwxrjEhfeqPenqHwrBmPQZjJGNSCHBkcF7"
               (base58/decode)
               (cvt/bytes->hex))))))


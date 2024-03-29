(ns btclj.ecc.secp256k1-test
  (:require [btclj.ecc.convert :refer [biginteger->hex]]
            [btclj.ecc.secp256k1 :as secp256k1]
            [btclj.ecc.sha256 :refer [hash256]]
            [btclj.ecc.signature :as sig]
            [clojure.test :refer [deftest is testing]]))

(deftest params-test
  (testing "generator has order n"
    (is (=
         [##Inf ##Inf]
         (secp256k1/mul
          secp256k1/G
          secp256k1/n)))))

(deftest verify-test
  (testing "verify returns true for valid signatures"
    (is (let [data 0xbc62d4b80d9e36da29c16c5d4d9f11731f36052c72401a76c23c0fb5a9b74423
              s 0x8ca63759c1157ebeaec0d03cecca119fc9a75bf8e6d0fa65c841c8e2738cdaec
              r 0x37206a0610995c58074999cb9767b87af4c4978db68c06e8e6e81d282047a7c6
              pub [0x04519fac3d910ca7e7138f7013706f619fa8f033e6ec6e09370ea38cee6a7574
                   0x82b51eab8c27c66e26c858a079bcdf4f1ada34cec420cafc7eac1a42216fb6c4]]
          (sig/valid? [r s] pub data))))

  (testing "verify returns false for invalid signatures"
    (is (let [data 0xbc62d4b80d9e36da29c16c5d4d9f00000036052c72401a76c23c0fb5a9b74423
              s 0x8ca63759c1157ebeaec0d03cecca119fc9a75bf8e6d0fa65c841c8e2738cdaec
              r 0x37206a0610995c58074999cb9767b87af4c4978db68c06e8e6e81d282047a7c6
              pub [0x04519fac3d910ca7e7138f7013706f619fa8f033e6ec6e09370ea38cee6a7574
                   0x82b51eab8c27c66e26c858a079bcdf4f1ada34cec420cafc7eac1a42216fb6c4]]
          (not (sig/valid? [r s] pub data))))

    (is (let [data 0xbc62d4b80d9e36da29c16c5d4d9f11731f36052c72401a76c23c0fb5a9b74423
              s 0x8ca63759c1157e1eaec0d03cecca119fc9a75bf8e6d0fa65c841c8e2738cdaec
              r 0x37206a0610995c58074999cb9767b87af4c4978db68c06e8e6e81d282047a7c6
              pub [0x04519fac3d910ca7e7138f7013706f619fa8f033e6ec6e09370ea38cee6a7574
                   0x82b51eab8c27c66e26c858a079bcdf4f1ada34cec420cafc7eac1a42216fb6c4]]
          (not (sig/valid? [r s] pub data))))))

(deftest signature-params-test
  (testing "signature parameters"
    (is (= ["28d003eab2e428d11983f3e97c3fa0addf3b42740df0d211795ffb3be2f6c52"
            "ae987b9ec6ea159c78cb2a937ed89096fb218d9e7594f02b547526d8cd309e2"
            "231c6f3d980a6b0fb7152f85cee7eb52bf92433d9919b9c5218cb08e79cce78"
            "2b698a0f0a4041b77e63488ad48c23e8e8838dd1fb7520408b121697b782ef22"
            "bb14e602ef9e3f872e25fad328466b34e6734b7a0fcd58b1eb635447ffae8cb9"]
           (let [e (hash256 "my secret")
                 z (hash256 "my message")
                 k 1234567890
                 r (secp256k1/->r k)
                 s (secp256k1/->s e k r z)
                 [x y] (secp256k1/mul secp256k1/G e)]
             (map biginteger->hex [x y z r s]))))

    (is (= ["f01d6b9018ab421dd410404cb869072065522bf85734008f105cf385a023a80f"
            "eba29d0f0c5408ed681984dc525982abefccd9f7ff01dd26da4999cf3f6a295"
            "969f6056aa26f7d2795fd013fe88868d09c9f6aed96965016e1936ae47060d48"
            "2b698a0f0a4041b77e63488ad48c23e8e8838dd1fb7520408b121697b782ef22"
            "1dbc63bfef4416705e602a7b564161167076d8b20990a0f26f316cff2cb0bc1a"]
           (let [e 12345
                 z (hash256 "Programming Bitcoin!")
                 k 1234567890
                 r (secp256k1/->r k)
                 s (secp256k1/->s e k r z)
                 [x y] (secp256k1/mul secp256k1/G e)]
             (map biginteger->hex [x y z r s]))))))

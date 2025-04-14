.PHONY: run-x run-x-name run-m run-m-name test ci uberjar run-uberjar clean

run-x:
	clojure -X:run-x

run-x-name:
	clojure -X:run-x :name '"Someone"'

run-m:
	clojure -M:run-m

run-m-name:
	clojure -M:run-m Via-Main

test:
	clojure -T:build test

ci:
	clojure -T:build ci

uberjar:
	clojure -T:build ci

run-uberjar:
	java -jar target/btclj-0.1.0-SNAPSHOT.jar

clean:
	rm -rf target

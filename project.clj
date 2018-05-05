(defproject
  deps-to-boot
  "1.0.0-SNAPSHOT"
  :resource-paths
  ["src"]
  :exclusions
  []
  :source-paths
  []
  :repositories
  [["clojars" {:url "https://clojars.org/repo/"}]
   ["maven-central" {:url "https://repo1.maven.org/maven2"}]]
  :mirrors
  []
  :dependencies
  [[org.clojure/clojure "1.9.0-alpha14"]
   [adzerk/bootlaces "0.1.13" :scope "test"]
   [sparkfund/boot-lein-generate "0.3.0" :scope "test"]])
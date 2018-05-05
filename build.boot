(set-env!
  :resource-paths #{"src"}
  :dependencies '[[org.clojure/clojure "1.9.0-alpha14"]

                  [adzerk/bootlaces "0.1.13" :scope "test"]
                  [sparkfund/boot-lein-generate "0.3.0" :scope "test"]])

(require '[adzerk.bootlaces :refer [push-snapshot]]
         'boot.lein)

(def +version+ "1.0.0-SNAPSHOT")

(task-options!
  push #(into % {:repo "deploy-clojars" :ensure-version +version+})
  pom {:project 'deps-to-boot
       :version +version+})

(when (> (.lastModified (clojure.java.io/file "build.boot"))
         (.lastModified (clojure.java.io/file "project.clj")))
      (boot.lein/write-project-clj))

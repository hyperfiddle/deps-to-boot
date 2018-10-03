(set-env!
  :resource-paths #{"src"}
  :dependencies '[[org.clojure/clojure "1.9.0"]

                  [adzerk/bootlaces "0.1.13" :scope "test"]
                  [sparkfund/boot-lein-generate "0.3.0" :scope "test"]])

(require '[adzerk.bootlaces :refer [push-snapshot]]
         'boot.lein)

(def +version+ "1.1.0-SNAPSHOT")

(task-options!
  push #(into % {:repo "deploy-clojars" :ensure-version +version+})
  pom {:project 'deps-to-boot
       :version +version+})

(deftask publish [f file PATH str]
         (if (.endsWith +version+ "-SNAPSHOT")
           (push-snapshot file)
           (do (merge-env!
                 :repositories [["deploy-clojars" {:url "https://clojars.org/repo/"
                                                   :username (System/getenv "CLOJARS_USER")
                                                   :password (System/getenv "CLOJARS_PASS")}]])
               (push
                 :file file
                 :ensure-release true
                 :repo "deploy-clojars"))))

(when (> (.lastModified (clojure.java.io/file "build.boot"))
         (.lastModified (clojure.java.io/file "project.clj")))
  (boot.lein/write-project-clj))

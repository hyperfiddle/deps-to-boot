(ns deps-to-boot.core)


(def deps-edn (-> "deps.edn" slurp clojure.edn/read-string))

(defn boot-deps [& [alias]]
  {:dependencies (->> (if alias
                        (-> (get-in deps-edn [:aliases alias])
                            (select-keys [:extra-deps :default-deps :override-deps])
                            vals
                            (->> (apply concat)))
                        (:deps deps-edn))
                      (reduce
                        (fn [deps [artifact info]]
                          (if-let [version (:mvn/version info)]
                            (conj deps
                                  (transduce cat conj [artifact version]
                                             (select-keys info [:classifier :extension :exclusions])))
                            deps))
                        []))
   :resource-paths (->> (if alias
                          (get-in deps-edn [:aliases alias :extra-paths])
                          (:paths deps-edn))
                        set)})

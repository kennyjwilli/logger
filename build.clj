(ns build
  (:require
    [clojure.tools.build.api :as b]
    [deps-deploy.deps-deploy :as dd]))

(def lib 'dev.kwill/logger)
(def version (format "1.0.%s" (b/git-count-revs nil)))
(def class-dir "target/classes")

(defn- jar-opts [opts]
  (assoc opts
    :lib lib :version version
    :jar-file "target/logger.jar"
    :scm {:tag (str "v" version)}
    :basis (b/create-basis {})
    :class-dir class-dir
    :target "target"
    :src-dirs ["src"]))

(defn jar
  [opts]
  (let [opts (jar-opts opts)]
    (b/delete {:path "target"})
    (b/write-pom opts)
    (b/copy-dir {:src-dirs ["resources" "src"] :target-dir class-dir})
    (b/jar opts)
    opts))

(defn deploy
  [opts]
  (let [{:keys [jar-file] :as opts} (jar-opts opts)]
    (dd/deploy {:installer :remote
                :artifact  (b/resolve-path jar-file)
                :pom-file  (b/pom-path (select-keys opts [:lib :class-dir]))}))
  opts)

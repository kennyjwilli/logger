(ns kwill.logback-example
  (:require
    [kwill.logger :as log]))

(comment
  ;; Start repl with -Dlogback.configurationFile=resources/logback-prod.xml
  (log/info {:msg "hello" :extra "data"})
  (log/info {:msg "hello" :throwable (ex-info "oops" {:extra "data"})})
  )

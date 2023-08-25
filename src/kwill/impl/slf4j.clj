(ns kwill.impl.slf4j
  "slf4j2 log implementation"
  (:import
    (org.slf4j Logger LoggerFactory)
    (org.slf4j.event Level)
    (org.slf4j.spi LoggingEventBuilder)))

(set! *warn-on-reflection* true)

(defn ^LoggingEventBuilder with-kvs
  [^LoggingEventBuilder builder kvs]
  (reduce
    (fn [^LoggingEventBuilder builder [k v]]
      (.addKeyValue builder ^String (str k) v))
    builder
    kvs))

(defn log
  [^Logger logger ^Level level ^String msg data & [throwable]]
  (-> logger
    (.atLevel level)
    (.setMessage msg)
    (cond-> throwable (.setCause throwable))
    (with-kvs data)
    (.log)))

(defmacro log*
  [{:keys [form-meta level msg data throwable]}]
  (let [{:keys [line]} form-meta]
    `(log (LoggerFactory/getLogger (str ~*ns*))
       ~(case level
          :trace `Level/TRACE
          :debug `Level/DEBUG
          :info `Level/INFO
          :warn `Level/WARN
          :error `Level/ERROR)
       ~msg
       (assoc ~data :logger.line_number ~line)
       ~throwable)))

(comment
  (log* {:level :info :msg "hello" :data {:a "a" :b "b"} :form-meta {:line 1}})
  (log* {:level :info :msg "hello" :data {:a "a" :b "b"} :throwable (ex-info "foo" {:d 1})})
  )

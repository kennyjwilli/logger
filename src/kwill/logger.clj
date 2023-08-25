(ns kwill.logger
  "API ns for the logger. Currently only support slf4j2."
  (:require
    [kwill.impl.slf4j :as slf4j]))

(def ^:dynamic *context* nil)

(defmacro log
  "Logs data at the specified logging level.

  Args:
    level - The logging level (e.g., :trace, :debug, :info, :warn, or :error)
    data  - The data to log. Should contain a :msg key for the log message."
  {:arglists '([level data])}
  [level data & {:keys [form-meta]}]
  `(let [data# ~data
         context# *context*]
     (slf4j/log*
       {:level     ~level
        :msg       (:msg data#)
        :data      (merge context# (dissoc data# :msg :throwable))
        :form-meta ~(or form-meta (meta &form))
        :throwable (:throwable data#)})))

(defmacro trace
  "Logs data at the TRACE level.

  Args:
    data - The data to log. Should contain a :msg key for the log message."
  [data]
  `(log :trace ~data :form-meta ~(meta &form)))

(defmacro debug
  "Logs data at the DEBUG level.

  Args:
    data - The data to log. Should contain a :msg key for the log message."
  [data]
  `(log :debug ~data :form-meta ~(meta &form)))

(defmacro info
  "Logs data at the INFO level.

  Args:
    data - The data to log. Should contain a :msg key for the log message."
  [data]
  `(log :info ~data :form-meta ~(meta &form)))

(defmacro warn
  "Logs data at the WARN level.

  Args:
    data - The data to log. Should contain a :msg key for the log message."
  [data]
  `(log :warn ~data :form-meta ~(meta &form)))

(defmacro error
  "Logs data at the ERROR level.

  Args:
    data - The data to log. Should contain a :msg key for the log message. Use
    the :throwable key to include the throwable in the message."
  [data]
  `(log :error ~data :form-meta ~(meta &form)))

(defmacro with-context
  "Establishes a new logging context for the enclosed code block. Will override
  any existing context. Use [[with-context+]] to merge to with the existing
  context.

  Args:
    context: KVs to include in all logs within `body`"
  [context & body]
  `(binding [*context* ~context]
     ~@body))

(defmacro with-context+
  "Establishes a new logging context for the enclosed code block. Will merge with
  any existing context.

  Args:
    context: KVs to include in all logs within `body`"
  [context & body]
  `(binding [*context* (conj (or *context* {}) ~context)]
     ~@body))

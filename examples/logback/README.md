# Logback Example

Example usage with Logback.

## Local

```clojure
(log/info {:msg "hello" :extra "data"})
2023-08-24 18:14:23,327 [nREPL-session-37365f2a-af86-4824-b13c-dbdc431c677a] INFO  kwill.logback-example -:extra="data" :logger.line_number="6"- hello
=> nil

(log/info {:msg "hello" :throwable (ex-info "oops" {:extra "data"})})
2023-08-24 18:14:40,313 [nREPL-session-37365f2a-af86-4824-b13c-dbdc431c677a] INFO  kwill.logback-example -:logger.line_number="7"- hello
clojure.lang.ExceptionInfo: oops
	at kwill.logback_example$eval2096.invokeStatic(logback_example.clj:1)
	at kwill.logback_example$eval2096.invoke(logback_example.clj:7)
	at clojure.lang.Compiler.eval(Compiler.java:7194)
	at clojure.lang.Compiler.eval(Compiler.java:7149)
	at clojure.core$eval.invokeStatic(core.clj:3215)
	at clojure.core$eval.invoke(core.clj:3211)
	at nrepl.middleware.interruptible_eval$evaluate$fn__978$fn__979.invoke
```

## Prod

In prod we log JSON with [logstash-logback-encoder](https://github.com/logfellow/logstash-logback-encoder). 

```clojure
(log/info {:msg "hello" :extra "data"})
{"timestamp":"2023-08-24T18:18:10.194735-07:00","message":"hello","logger_name":"kwill.logback-example","level":"INFO","level_value":20000,":extra":"data",":logger.line_number":7}
=> nil

(log/info {:msg "hello" :throwable (ex-info "oops" {:extra "data"})})
{"timestamp":"2023-08-24T18:18:12.895905-07:00","message":"hello","logger_name":"kwill.logback-example","level":"INFO","level_value":20000,"exception":"clojure.lang.ExceptionInfo: oops\n\tat kwill.logback_example$eval2035.invokeStatic...",":logger.line_number":8}
=> nil
```

# logger

A data-first logging approach.

## Installation

```clojure
dev.kwill/logger {:mvn/version "1.0.0"}
```

## Usage

To actually see log lines printed, you'll need to add a slf4j compatible logging implementation.
e.g., Logback, log4j2, etc.

```clojure
(require '[kwill.logger :as log])

(log/info {:msg "hello"})

(log/error {:msg "an error" :throwable (ex-info "oops" {:some "data"})})
```

All log functions accept a map of data to log. 
The only required key is `:msg`, specifying the log message.
The `:throwable` key can be optionally included to specify a `Throwable` to log.
All others keys are included as key-values in the log message using the new [fluent API](https://www.slf4j.org/manual.html#fluent).

## License

Copyright © 2023 Kenny Williams

Distributed under the MIT license. See the LICENSE file for more info.

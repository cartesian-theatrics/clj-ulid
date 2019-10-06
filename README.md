# clj-ulid

Basic ULID utils for clojure. Wraps [Sulky ULID] (https://github.com/huxi/sulky/tree/master/sulky-ulid).

# Usage

```clojure
(require [clj-ulid.core :as ulid])

(def my-ulid (ulid/ulid))
(def ^java.util.UUID my-uuid (ulid/ulid->uuid my-ulid))
(assert (= (ulid/ulid->string (ulid/uuid->ulid (ulid/ulid->uuid my-ulid)))
           (ulid/ulid->string my-ulid)))
```

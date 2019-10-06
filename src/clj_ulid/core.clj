(ns assist-analysis.ulid
  (:import de.huxhorn.sulky.ulid.ULID
           de.huxhorn.sulky.ulid.ULID$Value
           java.util.UUID
           [java.nio ByteBuffer]))

(defonce ^:private ulid-instance (ULID.))

(defn ulid
  "generates a ULID in string representation"
  ^String
  []
  (.nextULID ulid-instance))

(defn ulid-bytes
  "generates a ULID in binary representation"
  ^bytes
  []
  (.toBytes (.nextValue ulid-instance)))

(defn string->bytes
  "converts a ULID from string representation to binary representation"
  ^bytes
  [^String s]
  (.toBytes (ULID/parseULID s)))

(defn bytes->string
  "converts a ULID from binary representation to string representation"
  ^String
  [^bytes b]
  (.toString (ULID/fromBytes b)))


(defn to-bytes
  ^bytes
  [^ULID id]
  (.toBytes id))

(defn ulid->uuid [ulid-string]
  (let [bytes (string->bytes ulid-string)
        bb (ByteBuffer/wrap bytes)
        low (.getLong bb)
        hi (.getLong bb)]
    (UUID. low hi)))

(defn uuid->ulid [^java.util.UUID uuid]
  (let [hi (.getMostSignificantBits uuid)
        low (.getLeastSignificantBits uuid)
        buf (ByteBuffer/allocate (* 2 Long/BYTES))]
    (.putLong buf hi)
    (.putLong buf low)
    (ULID/fromBytes (.array buf))))

(defn uuid-string->ulid [uuid-string]
  (let [uuid (java.util.UUID/fromString uuid-string)]
    (.toString (uuid->ulid uuid))))

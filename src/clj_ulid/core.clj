(ns clj-ulid.core
  (:import de.huxhorn.sulky.ulid.ULID
           de.huxhorn.sulky.ulid.ULID$Value
           java.util.UUID
           [java.nio ByteBuffer]))

(defonce ^:private ulid-instance (ULID.))

(defn ulid
  "Generate a ULID object"
  ^ULID
  []
  (.nextValue ulid-instance))

(defn ulid-string
  "generates a ULID in string representation"
  ^String
  []
  (.nextULID ulid-instance))

(defn ulid->bytes
  "generates a ULID in binary representation"
  ^bytes
  []
  (.toBytes (.nextValue ulid-instance)))

(defn ulid->string ^String [^ULID ulid]
  (.toString ulid))

(defn string->ulid
  "Creates a ULID from a string representation."
  ^ULID
  [^String s]
  (ULID/parseULID s))

(defn bytes->ulid
  "converts a ULID from binary representation to string representation"
  ^String
  [^bytes b]
  (ULID/fromBytes b))

(defn ulid->bytes
  ^bytes
  [^ULID id]
  (.toBytes id))

(defn ulid->uuid ^java.util.UUID [^ULID ulid]
  (let [bytes (.toBytes ulid)
        bb (ByteBuffer/wrap bytes)
        low (.getLong bb)
        hi (.getLong bb)]
    (UUID. low hi)))

(defn uuid->ulid ^ULID [^java.util.UUID uuid]
  (let [hi (.getMostSignificantBits uuid)
        low (.getLeastSignificantBits uuid)
        buf (ByteBuffer/allocate (* 2 Long/BYTES))]
    (.putLong buf hi)
    (.putLong buf low)
    (ULID/fromBytes (.array buf))))
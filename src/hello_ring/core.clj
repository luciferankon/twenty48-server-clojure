(ns hello-ring.core
  (:require [ring.adapter.jetty :as jetty]))

(def no-of-hits (atom 0))

(defn first-handler
  [request]
  {:status  200
   :headers {"Content-Type" "text/plain"}
   :body    (str (swap! no-of-hits dec))})

(defn second-handler
  [request]
  {:status  200
   :headers {"Content-Type" "text/plain"}
   :body    (str (swap! no-of-hits inc))})

(defn handler [request]
  (cond
    (= (:uri request) "/") (first-handler request)
    (= (:uri request) "/hi") (second-handler request)))

(defn -main []
  (jetty/run-jetty handler {:port 3000}))
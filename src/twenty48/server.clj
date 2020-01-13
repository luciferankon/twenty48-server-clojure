(ns twenty48.server
  (:require [ring.adapter.jetty :as jetty])
  (:require [twenty48.core :as game]))

(def game-state (atom [[0 0 2 2] [2 2 0 0] [0 0 2 2] [2 2 0 0]]))

(defn generate-response
  [body]
  {:status  200
   :headers {"Content-Type" "text/plain"}
   :body    body})

(def create-game
  (generate-response (pr-str @game-state)))

(def move-left
  (generate-response (pr-str (swap! game-state game/play-left))))

(def move-right
  (generate-response (pr-str (swap! game-state game/play-right))))

(def move-top
  (generate-response (pr-str (swap! game-state game/play-top))))

(def move-bottom
  (generate-response (pr-str (swap! game-state game/play-bottom))))

(defn handle-routes
  [request]
  (cond
    (= (:uri request) "/") create-game
    (= (:uri request) "/left") move-left
    (= (:uri request) "/right") move-right
    (= (:uri request) "/top") move-top
    (= (:uri request) "/bottom") move-bottom))

(defn -main []
  (jetty/run-jetty handle-routes {:port 3000}))
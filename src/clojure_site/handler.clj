(ns clojure-site.handler
  (:require
    [clojure-site.routes :refer [tech-routes]]
    [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
    [compojure.route :as route]))

(def app
  (wrap-defaults tech-routes site-defaults))

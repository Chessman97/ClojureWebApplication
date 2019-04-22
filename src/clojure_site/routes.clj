(ns clojure-site.routes
  (:require

    ; работа с маршрутами
    [compojure.core :refer [defroutes GET POST]]
    [compojure.route :as route]

    ; контроллеры запросов
    [clojure-site.controllers :as c]

    ; отображение страниц
    [clojure-site.views :as v]

    ; функции взаимодействия с БД
    [clojure-site.db :as db]))

; объявляем маршруты
(defroutes tech-routes

           ; страница просмотра письма
           (GET "/tech/:id" [id]
             (let [tech (db/get-tech id)]
               (v/tech tech)))

           ; контроллер удаления письма по id
           (GET "/delete/:id" [id]
             (c/delete id))

           ; обработчик редактирования письма
           (POST "/edit/:id" request
             (-> c/edit))

           ; страница редактирования письма
           (GET "/edit/:id" [id]
             (let [tech (db/get-tech id)]
               (v/edit tech)))

           ; обработчик добавления письма
           (POST "/create" request
             (-> c/create))

           ; страница добавления письма
           (GET "/create" []
             (v/create))

           ; главная страница приложения
           (GET "/" []
             (let [techs (db/get-techs)]
               (v/index techs)))

           ; ошибка 404
           (route/not-found "Ничего не найдено"))
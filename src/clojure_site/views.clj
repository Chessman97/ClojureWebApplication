(ns clojure-site.views
  (:require

    ; "Шаблонизатор"
    [selmer.parser :as parser]

    ; для HTTP заголовков
    [ring.util.response :refer [content-type response]]

    ; для CSRF защиты
    [ring.util.anti-forgery :refer [anti-forgery-field]]))

; подскажем Selmer где искать наши шаблоны
(parser/set-resource-path! (clojure.java.io/resource "templates"))

; Добавим тэг с полем для форм в нем будет находится
; автоматически созданное поле с anti-forgery ключом
(parser/add-tag! :csrf-field (fn [_ _] (anti-forgery-field)))

(defn render [template & [params]]
  "Эта функция будет отображать наши html шаблоны
  и передавать в них данные"
  (-> template
      (parser/render-file
        ; Добавим к получаемым данным постоянные
        ; значения которые хотели бы получать
        ; на любой странице
        (assoc params
          :title "Специальная техника"
          :page (str template)))
      ; Из всего этого сделаем HTTP ответ
      response
      (content-type "text/html; charset=utf-8")))

(defn tech
  "Страница просмотра"
  [tech]
  (render "tech.html"
          ; передаем данные в шаблон
          {:tech tech}))

(defn edit
  "Страница редактирования"
  [tech]
  (render "edit.html"
          ; передаем данные в шаблон
          {:tech tech}))

(defn create
  "Страница создания"
  []
  (render "create.html"))

(defn index
  "Главная страница приложения"
  [techs]
  (render "index.html"
          ; Передаем данные в шаблон
          ; Если techs пуст вернуть false
          {:techs (if (not-empty techs)
                    techs false)}))
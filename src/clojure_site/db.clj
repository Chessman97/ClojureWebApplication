(ns clojure-site.db
  (:require

    ; непосредственно Monger
    [monger.core :as mg]
    [monger.collection :as m]
    [monger.operators :refer :all])

    ; Импортируем методы из Java библиотек
    (:import org.bson.types.ObjectId)
  )

; создадим переменную соединения с БД
(defonce db
         (let [uri "mongodb://127.0.0.1:27017/tech"
               {:keys [db]} (mg/connect-via-uri uri)]
           db))

(defn remove-tech
  "Удалить письмо по ее id"
  [id]
  ; переформатируем строку в id
  (let [id (ObjectId. id)]
    (m/remove-by-id db "tech" id)))

(defn update-tech
  "Обновить письмо по ее id"
  [id tech]
  ; Переформатируем строку в id
  ; todo редактирование не работает
  (let [id (ObjectId. id)]
    (m/update db "tech" {:_id id} tech)))

(defn get-tech
  "Получить письмо по ее id"
  [id]
  (let [id (ObjectId. id)]
    ; Эта функция вернет hash-map найденного документа
    (m/find-map-by-id db "tech" id)))

(defn get-techs
  "Получить все письма"
  []
  ; Find-maps возвращает все документы
  ; из коллеции в виде hash-map
  (m/find-maps db "tech"))

(defn create-tech
  "Создать письмо в БД"
  ; Наше письмо принимается от котролера
  ; и имеет тип hash-map c видом:
  ; {:subject "Заголовок" :text "Содержание"}
  [tech]
  ; Monger может сам создать id
  ; но разработчиками настоятельно рекомендуется
  ; добавить это поле самостоятельно
  (let [object-id (ObjectId.)]
    ; Нам остается просто передать hash-map
    ; функции создания документа, только
    ; добавим в него сгенерированный id
    ; и штамп даты и времени создания
    (m/insert db "tech" (assoc tech
                           :_id object-id))))


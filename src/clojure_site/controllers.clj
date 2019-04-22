(ns clojure-site.controllers
  (:require

    ; функция редиректа
    [ring.util.response :refer [redirect]]

    ; функция для взаимодействия с БД
    [clojure-site.db :as db]
    [compojure.route :as route]))

(defn delete
  "Контроллер удаления письма"
  [id]
  (do
    (db/remove-tech id)
    (redirect "/")))

(defn edit
  "Контроллер редактирования письма"
  [request]

  ; получаем данные из формы
  (let [tech-id (get-in request [:form-params "id"])
        tech {:name (get-in request [:form-params "name"])
              :modell  (get-in request [:form-params "modell"])
              :marka (get-in request [:form-params "marka"])
              :gruz (get-in request [:form-params "gruz"])
              :massa (get-in request [:form-params "massa"])
              :dvig (get-in request [:form-params "dvig"])
              :cost (get-in request [:form-params "cost"])}]

    ; проверяем данные
    (if (and (not-empty (:name tech))
             (not-empty (:modell tech))
             (not-empty (:marka tech))
             (not-empty (:gruz tech))
             (not-empty (:massa tech))
             (not-empty (:dvig tech))
             (not-empty (:cost tech)))
      ; все хорошо
      (do
        (db/update-tech tech-id tech)
        (redirect "/"))
      ; ошибка
      "Проверьте правильность введенных данных")))

(defn create
  "Контролер создания письма"
  [request]

  ; получаем данные из формы
  ; (ассоциативный массив)
  (let [tech {:name (get-in request [:form-params "name"])
              :modell  (get-in request [:form-params "modell"])
              :marka (get-in request [:form-params "marka"])
              :gruz (get-in request [:form-params "gruz"])
              :massa (get-in request [:form-params "massa"])
              :dvig (get-in request [:form-params "dvig"])
              :cost (get-in request [:form-params "cost"])}]

    ; Проверим данные
    (if (and (not-empty (:name tech))
             (not-empty (:modell tech))
             (not-empty (:marka tech))
             (not-empty (:gruz tech))
             (not-empty (:massa tech))
             (not-empty (:dvig tech))
             (not-empty (:cost tech)))

      ; все хорошо
      (do
        (db/create-tech tech)
        (redirect "/"))

      ; ошибка
      "Проверьте правильность введенных данных")))

(ns nadlan-gov-il.core
  (:require [clj-http.client :as http]))

(def api-url "https://www.nadlan.gov.il/Nadlan.REST/")

(defn get-assets-and-deals [{:keys [Gush Parcel] :as params}]
  (let [params (merge {:CurrentLavel 6 :PageNo 1} params)]
    (-> (http/post (str api-url "Main/GetAssestAndDeals")
                   {:as :json
                    :form-params params
                    :content-type :json})
        :body)))

(defn get-city-list []
  (-> (http/get (str api-url "Main/GetCitysList")
                {:query-params {:nb true
                                :st true}
                 :as :json})
      :body))

(defn get-neighborhoods-list-by-city [{:keys [CityName] :as params}]
  (-> (http/get (str api-url "Main/GetNeighborhoodsListByCity")
                {:query-params params
                 :as :json})
      :body))

(defn get-neighborhoods []
  (-> (http/get (str api-url "Main/GetNeighborhoodsListKey")
                {:query-params {:startWithKey -1}
                 :as :json})
      :body))

(defn get-streets-list-by-city [{:keys [CityName] :as params}]
  (-> (http/get (str api-url "Main/GetStreetsListByCityAndStartsWith")
               {:query-params (merge {:startWithKey -1} params)
                :as :json})
      :body))

(comment
  (count (get-assets-and-deals {:CurrentLavel 2 :ObjectID 6300}))
  (count (get-assets-and-deals {:Gush 6161 :Parcel 504}))
  (count (get-assets-and-deals {:Gush 6167 :Parcel 350}))
  (count (get-city-list))
  (count (get-neighborhoods-list-by-city {:CityName "ירושלים"}))
  (get-assets-and-deals {:CurrentLavel 7
                         :PageNo 1
                         :ObjectID 53741432}))

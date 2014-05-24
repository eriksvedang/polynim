(ns polynim.core
  (:gen-class)
  (:require [quil.core :refer :all]
            [polynim.poly :refer :all]
            [polynim.anim :refer :all]
            [polynim.util :refer [pulse fris]]))

(def p1 (make-poly [[10 10] [390 10] [390 390] [10 390]]))
(def p2 (make-poly [[200 100] [100 50] [100 200] [350 200]]))
(def p3 (make-poly [[10 390] [10 10] [390 10] [390 390]]))
(def p4 (make-poly [[300 200] [200 150] [20 300] [390 30]]))
(def p5 (make-poly [[10 390] [10 10]]))
(def p6 (make-poly [[390 10] [390 390]]))

(def a1 (make-anim [p1 p2 p3 p2] :color [0 200 255]))
(def a2 (make-anim [p2 p4] :speed 0.25))

(def data (atom [a1 a2]))

(defn setup []
  (frame-rate 60))

(defn update-all-anims [anims]
  (map #(update-anim % (fris 1.5)) anims))

(defn draw-fps []
  (fill 255)
  (text (str "Frame rate: " (int (current-frame-rate))) 10 20))

(defn draw []
  (when (< (pulse 2) 0.95)
    (background 40))
  (swap! data update-all-anims)
  (stroke-weight 3)
  (doall (map draw-anim @data))
  (no-stroke)
  #_(draw-fps))

(defn -main [& args]
  (sketch
   :title "P O L Y N I M"
   :size [400 400]
   :renderer :opengl
   :setup #'setup
   :draw #'draw))

(comment (-main))

@data

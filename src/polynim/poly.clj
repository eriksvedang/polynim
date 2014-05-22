(ns polynim.poly
  (:require [quil.core :refer :all]))

;;; point = [<int> <int>]
;;; Poly = {:points [<point>]}

(defrecord Poly [points])

(defn make-poly
  "Creates an instance of Poly."
  [points]
  (Poly. points))

(defn mix-floats
  "Interpolate two floats weighted with fraction 0 - 1."
  [x y fraction]
  (let [diff (- y x)]
    (+ x (* diff fraction))))

(defn mix-points
  "Interpolate two points weighted with fraction 0 - 1."
  [p1 p2 fraction]
  (map #(mix-floats %1 %2 fraction) p1 p2))

(defn mix-polys
  "Interpolate two Poly:s weighted with fraction 0 - 1.
   Returns a new Poly with default settings but interpolated points."
  [poly1 poly2 fraction]
  (make-poly (map #(mix-points %1 %2 fraction)
                  (:points poly1) (:points poly2))))

(defn draw-poly
  "Render a Poly to the screen."
  [poly closed-loop]
  (let [points (:points poly)]
    (loop [prev nil
           points-left points]
      (let [curr (first points-left)]
        (when (and prev curr)
          (line prev curr))
        (when curr
          (recur curr (rest points-left)))))
    (when closed-loop
      (line (first points) (last points)))))

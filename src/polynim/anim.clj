(ns polynim.anim
  (:require [quil.core :refer [stroke]]
            [polynim.poly :refer :all]
            [polynim.util :refer [fris]]))

(defrecord Anim [polys speed frame color closed-loop])

(defn make-anim
  "Creates an instance of Anim, starting at frame 0.
   Optional arguments are speed, color, closed-loop."
  [polys & {:keys [speed color closed-loop]
            :or {speed 1.0
                 color [230 100 100]
                 closed-loop true}}]
  (Anim. polys speed 0.0 color closed-loop))

(defn draw-anim
  "Draws an instance of Anim, interpolating between the current frame and the next one."
  [anim]
  (let [polys (:polys anim)
        frame-count (count polys)
        frame (:frame anim)
        frame-int (int frame)
        current-frame (mod frame-int frame-count)
        next-frame (mod (inc current-frame) frame-count)
        current-poly (nth polys current-frame)
        next-poly (nth polys next-frame)
        fraction (- frame frame-int)
        maybe-color (:color anim)
        closed-loop (:closed-loop anim)]
    (when maybe-color
      (apply stroke maybe-color))
    (draw-poly (mix-polys current-poly next-poly fraction) closed-loop)))

(defn update-anim
  "Advance the frame counter for the anim with dt seconds."
  [anim dt]
  (update-in anim [:frame] + (* (:speed anim) dt)))

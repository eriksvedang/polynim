(ns polynim.util
  (:require [quil.core :refer :all]))

(defn fris
  "Frame rate independent speed."
  [speed]
  (* speed (/ 1.0 (current-frame-rate))))

(defn pulse
  "Pulsating value between 0 and 1 with rate (cycles/second)."
  [rate]
  (abs (sin (* 0.001 rate (millis)))))
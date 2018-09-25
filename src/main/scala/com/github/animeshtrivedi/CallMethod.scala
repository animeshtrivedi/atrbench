package com.github.animeshtrivedi

import scala.util.Random

/**
  * Created by atr on 05.12.17.
  */
class CallMethod {
  private[this] val constantInteger = new Random(System.nanoTime()).nextInt()

  final def multiplyMethod(value:Int):Int = constantInteger ^ value
}

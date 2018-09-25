package com.github.animeshtrivedi

import scala.util.Random

/**
  * Created by atr on 05.12.17.
  */
class CallFunction {
  private[this] val constantInteger = new Random(System.nanoTime()).nextInt()

  private[this] val multiplyFunctionVariable:Int=>Int = (value:Int) => constantInteger ^ value

  final def multiplyFunction(value:Int):Int = multiplyFunctionVariable(value)
}

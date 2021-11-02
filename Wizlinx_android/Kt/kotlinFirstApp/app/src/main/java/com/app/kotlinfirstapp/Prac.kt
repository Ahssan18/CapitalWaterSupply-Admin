package com.app.kotlinfirstapp

import java.util.*

enum class Weak{
    Monday,Tuewsday,Wednesday,Thirsday
}
sealed class Weaks{
    class Monday(a:String): Weaks()
    class Tuesday(b:Int):Weaks()
    class Thirsday(c:Float):Weaks()
}
fun main()
{
    println(Weak.Monday)
    var ob=Weaks.Monday("hellow")
    checkDay(ob)
}

fun checkDay(obj:Weaks)
{
    when(obj)
    {
        is Weaks.Monday -> print("its Monday")
        is Weaks.Tuesday -> print("its Monday")
    }
}
fun <T> getDataType(a:T,b:T): T? {
    var c=a+b
    return (a+b);
}
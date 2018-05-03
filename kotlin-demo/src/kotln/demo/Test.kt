package kotln.demo

import org.apache.commons.lang3.StringUtils


fun StringUtils.ahoj(): String {
    return "ahoj";
}

class Test(@Volatile private var a: Int = 22, var b: String = "32") {


    fun getA(): Int = a


}


fun main(args: Array<String>) {

    val test = Test(b = "44")
    test.getA()

    println(test.getA())
    println(test.b)

    val util =  StringUtils();
    util.ahoj();
    println(util.ahoj())


}


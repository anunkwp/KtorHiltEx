package com.nunkung.myapplication

/**
 * Created by「 Nun Kung 」on on 07/03/2022 :)
 */

private fun findPrimeNo(number: Int): Boolean {
    if (number < 2) return false
    for (i in 2.toLong()..number / 2) {
        if (number % i == 0.toLong()) {
            return false
        }
    }
    return true
}

private fun main555() {
    val list: MutableList<Int> = mutableListOf()
    for (i in 1..23) {
        if (findPrimeNo(i)) {
            list.add(i)
        }
    }
    println("Prime Numbers from : $list")
}

private fun String.toSingleDigitList() = map {
    "$it".toIntOrNull()
}.filterNotNull()

private fun main() {
    val number = "911"
    val digits = number.toSingleDigitList()
    var stringThai = ""
    val numString = listOf("", "หนึ่ง", "สอง", "สาม", "สี่", "ห้า", "หก", "เจ็ด", "แปด", "เก้า", "สิบ")
    val numString1 = listOf("", "เอ็ด", "สอง", "สาม", "สี่", "ห้า", "หก", "เจ็ด", "แปด", "เก้า", "สิบ")
    val numString2 = listOf("ยี่", "สาม", "สี่", "ห้า", "หก", "เจ็ด", "แปด", "เก้า")
    val numDigit = listOf("สิบ", "ร้อย")
    when (number.length) {
        1 -> {
            stringThai = numString[number.toInt()]
        }
        2 -> {
            stringThai = if (digits[0] >= 2) {
                numString2[digits[0] - 2] + numDigit[number.length - 2] + numString1[digits[1]]
            } else {
                numDigit[number.length - 2] + numString1[digits[1]]
            }
        }
        3 -> {
            stringThai = if (digits[1] != 1) {
                numString[digits[0]] + numDigit[number.length - 2] + numString2[digits[1] - 2] + numDigit[number.length - 3] + numString1[digits[2]]
            } else {
                numString[digits[0]] + numDigit[number.length - 2] + numString2[digits[1] - 1] + numDigit[number.length - 3] + numString1[digits[2]]
            }
        }
    }
    println(stringThai + "บาท")
}

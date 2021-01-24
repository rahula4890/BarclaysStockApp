package com.barclays.stockapp.extensions

fun Float.convertPriceToString(): String {
    return "$$this"
}
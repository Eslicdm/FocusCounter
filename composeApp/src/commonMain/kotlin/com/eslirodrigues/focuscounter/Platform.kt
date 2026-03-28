package com.eslirodrigues.focuscounter

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
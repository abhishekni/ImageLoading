package com.abhishekn.techm

import java.io.InputStreamReader

class MockResponseReader(filePath : String) {
    val fileContent : String
    init {
        val reader = InputStreamReader(MockResponseReader::class.java.getResourceAsStream(filePath))
        fileContent = reader.readText()
        reader.close()
    }
}
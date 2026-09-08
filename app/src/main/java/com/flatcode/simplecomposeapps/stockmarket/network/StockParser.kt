package com.flatcode.simplecomposeapps.stockmarket.network

import com.flatcode.simplecomposeapps.stockmarket.model.CompanyListing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

interface CSVParser<T> {
    suspend fun parse(stream: InputStream): List<T>
}

@Singleton
class CompanyListingsParser @Inject constructor() : CSVParser<CompanyListing> {
    override suspend fun parse(stream: InputStream): List<CompanyListing> {
        return withContext(Dispatchers.IO) {
            val reader = BufferedReader(InputStreamReader(stream))
            reader.useLines { lines ->
                lines.drop(1) // Drop header
                    .mapNotNull { line ->
                        val fields = line.split(",")
                        if (fields.size < 3) return@mapNotNull null
                        CompanyListing(
                            symbol = fields[0].trim(),
                            name = fields[1].trim(),
                            exchange = fields[2].trim()
                        )
                    }.toList()
            }
        }
    }
}
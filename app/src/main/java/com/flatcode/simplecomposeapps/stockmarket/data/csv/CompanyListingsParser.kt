package com.flatcode.simplecomposeapps.stockmarket.data.csv

import com.flatcode.simplecomposeapps.stockmarket.domain.model.CompanyListing
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject

class CompanyListingsParser @Inject constructor() : CSVParser<CompanyListing> {
    override suspend fun parse(stream: InputStream): List<CompanyListing> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            csvReader.readAll().drop(1).mapNotNull { line ->
                CompanyListing(
                    name = line.getOrNull(1) ?: return@mapNotNull null,
                    symbol = line.getOrNull(0) ?: return@mapNotNull null,
                    exchange = line.getOrNull(2) ?: return@mapNotNull null
                )
            }.also { csvReader.close() }
        }
    }
}
package com.flatcode.simplecomposeapps.stockmarket.network

import com.flatcode.simplecomposeapps.stockmarket.data.StockDao
import com.flatcode.simplecomposeapps.stockmarket.data.toCompanyListing
import com.flatcode.simplecomposeapps.stockmarket.data.toCompanyListingEntity
import com.flatcode.simplecomposeapps.stockmarket.model.CompanyListing
import com.flatcode.simplecomposeapps.stockmarket.utils.Resource
import io.ktor.client.statement.bodyAsChannel
import io.ktor.utils.io.jvm.javaio.toInputStream
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepository @Inject constructor(
    private val api: StockApi,
    private val dao: StockDao,
    private val parser: CSVParser<CompanyListing>,
) {
    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String,
    ): Flow<Resource<List<CompanyListing>>> = flow {
        emit(Resource.Loading(true))
        val local = dao.searchCompanyListing(query)
        emit(Resource.Success(data = local.map { it.toCompanyListing() }))

        if (local.isNotEmpty() && !fetchFromRemote && query.isBlank()) {
            emit(Resource.Loading(false))
            return@flow
        }

        try {
            val response = api.getListings()
            val remote = parser.parse(response.bodyAsChannel().toInputStream())
            dao.clearCompanyListings()
            dao.insertCompanyListings(remote.map { it.toCompanyListingEntity() })
            emit(
                Resource.Success(
                    data = dao.searchCompanyListing("").map { it.toCompanyListing() })
            )
        } catch (_: Exception) {
            emit(Resource.Error("Error loading data"))
        } finally {
            emit(Resource.Loading(false))
        }
    }
}

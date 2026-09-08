package com.flatcode.simplecomposeapps.countries.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Country(
    @ColumnInfo(name = "name") @SerialName("name") val countryName: String?,
    @ColumnInfo(name = "region") @SerialName("region") val countryRegion: String?,
    @ColumnInfo(name = "capital") @SerialName("capital") val countryCapital: String?,
    @ColumnInfo(name = "currency") @SerialName("currency") val countryCurrency: String?,
    @ColumnInfo(name = "language") @SerialName("language") val countryLanguage: String?,
    @ColumnInfo(name = "flag") @SerialName("flag") val imageURL: String?
) {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}
